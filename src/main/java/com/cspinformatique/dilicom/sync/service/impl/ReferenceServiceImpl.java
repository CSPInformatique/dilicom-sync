package com.cspinformatique.dilicom.sync.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cspinformatique.concurrent.Executor;
import com.cspinformatique.concurrent.Task;
import com.cspinformatique.dilicom.sync.entity.Reference;
import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;
import com.cspinformatique.dilicom.sync.entity.ReferenceNotification.Status;
import com.cspinformatique.dilicom.sync.entity.ReferenceRequest;
import com.cspinformatique.dilicom.sync.repository.elasticsearch.ReferenceRepository;
import com.cspinformatique.dilicom.sync.service.ConfigEntryService;
import com.cspinformatique.dilicom.sync.service.ReferenceNotificationService;
import com.cspinformatique.dilicom.sync.service.ReferenceRequestService;
import com.cspinformatique.dilicom.sync.service.ReferenceService;
import com.cspinformatique.dilicom.sync.util.DilicomConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReferenceServiceImpl implements ReferenceService {
	private static final Logger logger = LoggerFactory
			.getLogger(ReferenceServiceImpl.class);

	private static final int RESULTS_PER_PAGE = 30;

	@Autowired
	private ConfigEntryService configEntryService;

	@Autowired
	private DilicomConnector dilicomConnector;

	@Autowired
	private ReferenceNotificationService referenceNotificationService;

	@Autowired
	private ReferenceRequestService referenceRequestService;

	@Autowired
	private ReferenceRepository referenceRepository;

	@Resource
	private Environment env;

	private boolean referencePageloadCompleted;
	private boolean referenceloadCompleted = false;
	private boolean pageRequestLoadInProgress;
	private AtomicInteger page;
	private boolean referenceLoadInProgress;

	@Override
	public void exportDump() {
		try {
			int page = 0;
			int referencesDumped = 0;

			ObjectMapper objectMapper = new ObjectMapper();
			Page<Reference> pageResult = null;

			File referencesFile = new File("dump/references.data");

			if (!referencesFile.exists()) {
				referencesFile.mkdirs();
				referencesFile.createNewFile();
			} else {
				referencesFile.delete();
				referencesFile.createNewFile();
			}

			do {
				pageResult = referenceRepository.findAll(new PageRequest(page,
						500));

				for (Reference reference : pageResult.getContent()) {
					FileUtils.write(referencesFile,
							objectMapper.writeValueAsString(reference) + "\n",
							true);
				}

				referencesDumped += pageResult.getSize();

				logger.info(referencesDumped
						+ " references dumped. Dump file reached size "
						+ FileUtils.sizeOf(referencesFile) / (1000 * 1000)
						+ "mb");

				++page;
			} while (pageResult != null && !pageResult.isLast());
		} catch (JsonProcessingException jsonProcessingEx) {
			throw new RuntimeException(jsonProcessingEx);
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}
	}

	private synchronized ReferenceNotification findNextReferenceToLoad() {
		ReferenceNotification referenceNotification = this.referenceNotificationService
				.findNextReferenceToProcess();

		if (referenceNotification != null) {
			referenceNotification.setStatus(Status.PROCESSING);

			this.referenceNotificationService.save(referenceNotification);
		}

		return referenceNotification;
	}

	@Override
	public void hideReference(String ean13) {
		Reference reference = this.referenceRepository.findOne(ean13);

		if (reference == null) {
			logger.error("Reference " + ean13 + " doesn't exists.");

			return;
		}

		reference.setHided(true);

		this.referenceRepository.save(reference);
	}

	@Override
	public void importDump() {
		try {
			long importedReferences = 0;
			File referencesFile = new File("dump/references.data");
			ObjectMapper objectMapper = new ObjectMapper();

			List<Reference> references = new ArrayList<Reference>();
			for (String line : FileUtils.readLines(referencesFile)) {
				references.add(objectMapper.readValue(line, Reference.class));

				if (references.size() == 500) {
					importedReferences += references.size();
					this.referenceRepository.save(references);
					references.clear();

					logger.info("Imported " + importedReferences
							+ " references.");

				}
			}

			importedReferences += references.size();
			this.referenceRepository.save(references);

			logger.info("Import completed. " + importedReferences
					+ " references imported.");

		} catch (JsonProcessingException jsonProcessingEx) {
			throw new RuntimeException(jsonProcessingEx);
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}
	}

	@PostConstruct
	private void init() {
		System.out.println("ReferenceService initialized.");
	}

	@Scheduled(fixedRate = 1000 * 60)
	private void loadLatestReferences() {
		if(isDataImportationFromDilicomActivated()){
			this.loadReferencePages(true,
					this.referenceRequestService.findPageNextPageToProcess());
		}
	}

	@Override
	public Reference loadReferenceFromDilicomUrl(String dilicomUrl) {
		return this.dilicomConnector.loadReferenceFromUrl(dilicomUrl);
	}

	private void loadReferencePages(boolean fullLoad, int startPage) {
		if (!isRunningInsideBusinessHours()) {
			if (pageRequestLoadInProgress) {
				throw new RuntimeException("A load is currently in progress.");
			}

			this.pageRequestLoadInProgress = true;
			this.page = new AtomicInteger(startPage);

			this.dilicomConnector.initializeSearch(fullLoad);

			Executor executor = new Executor("Reference URL loader");
			executor.start();

			do {
				if (!isRunningInsideBusinessHours()) {
					executor.publishNewTask(new Task() {
						@Override
						public long execute() {
							int pageIndex = page.getAndIncrement();

							try {
								// Flags page request processing as a page to
								// process.
								referenceRequestService
										.save(new ReferenceRequest(
												pageIndex,
												ReferenceRequest.Status.TO_PROCESS,
												null));

								// Retreives all the references from the page.
								List<ReferenceNotification> referenceNotifications = new ArrayList<ReferenceNotification>();
								for (String referenceUrl : dilicomConnector
										.searchReferences(pageIndex, false,
												RESULTS_PER_PAGE)) {
									referenceNotifications
											.add(new ReferenceNotification(
													referenceUrl, new Date(),
													Status.TO_PROCESS, null));
								}

								// Persists the references.
								referenceNotificationService
										.saveAll(referenceNotifications);

								if (referenceNotifications.size() < RESULTS_PER_PAGE) {
									referencePageloadCompleted = true;
								}

								// Flags page request processing as completed.
								referenceRequestService
										.save(new ReferenceRequest(pageIndex,
												ReferenceRequest.Status.OK,
												null));
							} catch (Exception ex) {
								try {
									referenceRequestService
											.save(new ReferenceRequest(
													pageIndex,
													ReferenceRequest.Status.ERROR,
													ExceptionUtils
															.getFullStackTrace(ex)));
								} catch (Exception ex2) {
									logger.error(
											"Error while persisting reference request status.",
											ex2);
								}

								logger.error("Error while processing page "
										+ pageIndex + ".", ex);
							}

							return RESULTS_PER_PAGE;
						}
					});
				} else {
					referencePageloadCompleted = true;
				}
			} while (!referencePageloadCompleted);

			executor.waitForCompletion();

			this.pageRequestLoadInProgress = false;
		}
	}

	@Scheduled(fixedRate = 1000)
	private void loadReferences() {
		if (isDataImportationFromDilicomActivated() && !isRunningInsideBusinessHours()) {
			if (referenceLoadInProgress) {
				throw new RuntimeException("A load is currently in progress.");
			}

			this.referenceLoadInProgress = true;

			Executor executor = new Executor("Reference loader");

			executor.start();

			do {
				if (!isRunningInsideBusinessHours()) {
					executor.publishNewTask(new Task() {
						@Override
						public long execute() {
							ReferenceNotification referenceNotification = null;
							try {
								referenceNotification = findNextReferenceToLoad();

								if (referenceNotification == null) {
									referenceloadCompleted = true;
								} else {
									save(dilicomConnector
											.loadReferenceFromUrl(referenceNotification
													.getUrl()));

									referenceNotification.setStatus(Status.OK);
								}
							} catch (Exception ex) {
								if (referenceNotification != null) {
									try {
										referenceNotification
												.setStatus(Status.ERROR);
										referenceNotification
												.setCause(ExceptionUtils
														.getFullStackTrace(ex));
									} catch (Exception ex2) {
										logger.error(
												"Error while handling reference notification error from "
														+ referenceNotification
																.getUrl(), ex2);
									}

									logger.error(
											"Error while processing reference notification from "
													+ referenceNotification
															.getUrl(), ex);
								} else {
									logger.error("Error", ex);
								}
							} finally {
								if (referenceNotification != null) {
									try {
										referenceNotificationService
												.save(referenceNotification);
									} catch (Exception ex) {
										logger.error(
												"Error while processing reference notification from "
														+ referenceNotification
																.getUrl(), ex);
									}
								}
							}

							return 1;
						}
					});
				} else {
					referenceloadCompleted = true;
				}
			} while (!referenceloadCompleted);

			executor.waitForCompletion();

			this.referenceLoadInProgress = false;
		}
	}

	@Override
	public void publishToOdoo(String ean13) {
		ReferenceNotification notification = new ReferenceNotification(ean13,
				new Date(), ReferenceNotification.Status.ERROR, null);
		try {
			logger.warn("Publication to ERP not yet implemented");

			notification.setStatus(ReferenceNotification.Status.OK);
		} catch (Exception ex) {
			logger.error("Error while publishing reference " + ean13
					+ " to ERP system.", ex);

			notification.setCause(ExceptionUtils.getStackTrace(ex));

			throw new RuntimeException(ex);
		} finally {
			// save the notification status.
		}
	}

	private boolean isDataImportationFromDilicomActivated(){
		Boolean result = this.env.getProperty("dilicomsync.dilicom.importDate", Boolean.class);
		
		if(result == null){
			return false;
		}
		
		return result;
	}

	public boolean isRunningInsideBusinessHours() {
		Calendar calendar = Calendar.getInstance();

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			return false;
		}

		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		if (hourOfDay < 8 || hourOfDay > 21) {
			return false;
		}

		return true;
	}

	@Override
	public void save(Iterable<Reference> references) {
		this.referenceRepository.save(references);
	}

	@Override
	public void save(Reference reference) {
		this.referenceRepository.save(reference);
	}

	@Override
	public Page<Reference> search(Pageable pageable) {
		return this.referenceRepository.findAll(pageable);
	}

	@Override
	public Page<Reference> search(boolean loadedIntoErp, Pageable pageable) {
		return this.referenceRepository.findByLoadedIntoErp(loadedIntoErp,
				pageable);
	}

	@Override
	public Page<Reference> search(boolean hided, boolean loadedIntoErp,
			Pageable pageable) {
		return this.referenceRepository.findByHidedAndLoadedIntoErp(hided,
				loadedIntoErp, pageable);
	}

	@Override
	public Page<Reference> searchByHided(boolean hided, Pageable pageable) {
		return this.referenceRepository.findByHided(hided, pageable);
	}

	@Override
	public Page<Reference> searchByTitle(String title, Pageable pageable) {
		return this.referenceRepository.findByTitleLike(title, pageable);
	}

	@Override
	public Page<Reference> searchByTitle(String title, boolean loadedIntoErp,
			Pageable pageable) {
		return this.referenceRepository.findByTitleLikeAndLoadedIntoErp(title,
				loadedIntoErp, pageable);
	}

	@Override
	public Page<Reference> searchByTitle(String title, boolean hided,
			boolean loadedIntoErp, Pageable pageable) {
		return this.referenceRepository
				.findByTitleLikeAndHidedAndLoadedIntoErp(title, hided,
						loadedIntoErp, pageable);
	}

	@Override
	public Page<Reference> searchByTitleAndHided(String title, boolean hided,
			Pageable pageable) {
		return this.referenceRepository.findByTitleLikeAndHided(title, hided,
				pageable);
	}

	@Override
	public void unhideReference(String ean13) {
		Reference reference = this.referenceRepository.findOne(ean13);

		if (reference == null) {
			logger.error("Reference " + ean13 + " doesn't exists.");

			return;
		}

		reference.setHided(false);

		this.referenceRepository.save(reference);
	}
}
