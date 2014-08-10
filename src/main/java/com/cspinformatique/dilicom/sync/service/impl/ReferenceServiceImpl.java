package com.cspinformatique.dilicom.sync.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.Reference;
import com.cspinformatique.dilicom.sync.repository.ReferenceRepository;
import com.cspinformatique.dilicom.sync.service.ReferenceService;
import com.cspinformatique.dilicom.sync.util.DilicomConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReferenceServiceImpl implements ReferenceService {
	private static final Logger logger = LoggerFactory
			.getLogger(ReferenceServiceImpl.class);
	
	private static final int MAX_CONCURRENT_THREAD = 25;
	private static final int RESULTS_PER_PAGE = 30;
	
	@Autowired
	private DilicomConnector dilicomConnector;
	
	@Autowired
	private ReferenceRepository referenceRepository;

	private boolean loadCompleted ;
	private boolean loadInProgress;
	private AtomicInteger page;
	private int threadCount;
	
	@Override
	public void exportDump(){
		try {
			int page = 0;
			int referencesDumped = 0;
			
			ObjectMapper objectMapper = new ObjectMapper();
			Page<Reference> pageResult = null;
			
			File referencesFile = new File("dump/references.data");
			
			if(!referencesFile.exists()){
				referencesFile.mkdirs();
				referencesFile.createNewFile();
			}else{
				referencesFile.delete();
				referencesFile.createNewFile();
			}
			
			do{
				pageResult = referenceRepository.findAll(new PageRequest(page, 500));
				
				for(Reference reference : pageResult.getContent()){
					FileUtils.write(referencesFile, objectMapper.writeValueAsString(reference) + "\n", true);
				}
				
				referencesDumped += pageResult.getSize();
				
				logger.info(referencesDumped + " references dumped. Dump file reached size " + FileUtils.sizeOf(referencesFile) / (1000 * 1000) + "mb");
				
				++page;
			}while(pageResult != null && !pageResult.isLast());
		} catch (JsonProcessingException jsonProcessingEx) {
			throw new RuntimeException(jsonProcessingEx);
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}
	}
	
	@Override
	public void importDump(){
		try {
			long importedReferences = 0;
			File referencesFile = new File("dump/references.data");
			ObjectMapper objectMapper = new ObjectMapper();
			
			List<Reference> references = new ArrayList<Reference>();
			for(String line : FileUtils.readLines(referencesFile)){
				references.add(objectMapper.readValue(line, Reference.class));
				
				
				if(references.size() == 500){
					importedReferences += references.size();
					this.referenceRepository.save(references);
					references.clear();
					
					logger.info("Imported " + importedReferences + " references.");
					
				}
			}

			importedReferences += references.size();
			this.referenceRepository.save(references);
			

			logger.info("Import completed. " + importedReferences + " references imported.");
			
		} catch (JsonProcessingException jsonProcessingEx) {
			throw new RuntimeException(jsonProcessingEx);
		} catch (IOException ioEx) {
			throw new RuntimeException(ioEx);
		}
	}

	@PostConstruct
	private void init(){
		System.out.println("ReferenceService initialized.");
	}

	@Override
	public void initializeReferencesIndex() {
		this.loadReferences(true, 0);
	}

	@Override
	public void initializeReferencesIndex(int startPageIndex) {
		this.loadReferences(true, startPageIndex);
	}
	
	@Override
	public void loadLatestReferences() {
		this.loadReferences(false, 0);
	}
	
	@Override
	public Reference loadReferenceFromDilicomUrl(String dilicomUrl){
		return this.dilicomConnector.loadReferenceFromUrl(dilicomUrl, true);
	}

	private void loadReferences(boolean fullLoad, int startPage){
		if(loadInProgress){
			throw new RuntimeException("A load is currently in progress.");
		}
		
		this.loadInProgress = true;
		this.page = new AtomicInteger(startPage);
		this.threadCount = 0;
		
		this.dilicomConnector.initializeSearch(fullLoad);
		
		do{
			if(this.threadCount < MAX_CONCURRENT_THREAD){
				++threadCount;
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						Date startDate = new Date();
						
						int pageIndex = page.incrementAndGet();
						
						List<Reference> references = dilicomConnector.searchReferences(pageIndex, false, RESULTS_PER_PAGE);
						
						referenceRepository.save(references);
						
						logger.info(referenceRepository.count() + " references available after requesting page " + pageIndex + ".");
						
						if(references.size() < RESULTS_PER_PAGE){
							loadCompleted = true;
						}

						--threadCount;
						
						Date endDate = new Date();
						
						long processingTime = endDate.getTime() - startDate.getTime();
						
						double referencesProcessedByHour = (RESULTS_PER_PAGE * (1000 * 60 * 60)) / processingTime;
						
						logger.info("Loading " + referencesProcessedByHour * MAX_CONCURRENT_THREAD + " per hour.");
					}
				}).start();
			}else{
				try{
					Thread.sleep(200);
				}catch(InterruptedException interruptedEx){
					throw new RuntimeException(interruptedEx);
				}
			}
		}while(!loadCompleted);
		
		this.loadInProgress = false;
	}
	
	@Override
	public void loadReferencesIntoErp(List<Reference> references){
		
		for(Reference reference : references){
			reference.setLoadedIntoErp(true);
		}
		
		this.save(references);
	}
	
	@Override
	public void save(Reference reference){
		this.referenceRepository.save(reference);
	}
	
	@Override
	public void save(Iterable<Reference> references){
		this.referenceRepository.save(references);
	}
	
	@Override
	public Page<Reference> search(Pageable pageable){
		return this.referenceRepository.findAll(pageable);
	}
	
	@Override
	public Page<Reference> search(boolean loadedIntoErp, Pageable pageable){
		return this.referenceRepository.findByLoadedIntoErp(loadedIntoErp, pageable);
	}
	
	@Override
	public Page<Reference> searchByTitle(String title, Pageable pageable){
		return this.referenceRepository.findByTitleLike(title, pageable);
	}
	
	@Override
	public Page<Reference> searchByTitle(String title, boolean loadedIntoErp, Pageable pageable){
		return this.referenceRepository.findByTitleLikeAndLoadedIntoErp(title, loadedIntoErp, pageable);
	}
}
