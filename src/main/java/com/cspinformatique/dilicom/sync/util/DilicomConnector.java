package com.cspinformatique.dilicom.sync.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cspinformatique.dilicom.sync.entity.Reference;

@Component
@PropertySource("classpath:config/backends/dilicom.properties")
public class DilicomConnector {
	private static final Logger logger = LoggerFactory
			.getLogger(DilicomConnector.class);

	private static String PUBLICATION_DATE_FORMAT = "DD/MM/YYYY";

	@Resource
	private Environment env;

	private boolean initialized;
	private String tomcatid;
	private String jSessionId;
	private RestTemplate restTemplate;

	public DilicomConnector() {
		this.restTemplate = new RestTemplate();
	}

	private void checkForSearchError(ResponseEntity<String> responseEntity) {
		// Check for error
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException(
					"Error while requesting new references : "
							+ responseEntity.getStatusCode());
		} else if (!responseEntity.getBody().contains(
				"<span>Liste des articles</span>")) {
			throw new RuntimeException("Response doesn't match expected one.");
		}
	}

	private HttpHeaders generateHeaders() {
		return this.generateHeaders(null);
	}

	private HttpHeaders generateHeaders(String contentType) {
		HttpHeaders headers = new HttpHeaders();

		if (contentType != null) {
			headers.add("Content-Type", contentType);
		}

		if (jSessionId != null) {
			headers.add("COOKIE", tomcatid + ";" + jSessionId + ";");
		}

		return headers;
	}

	public void initializeSearch(boolean fullLoad) {
		this.logoutDilicom();

		this.loginToDilifac();

		this.loginToDilicom();

		this.openSearchPage();

		String url = "https://dilicom-prod.centprod.com/catalogue/consulter_articles.html;jsessionid="
				+ jSessionId.substring(11) + "?page=1&newsearch=false";
		String release = "ONE_MONTH";
		if (fullLoad) {
			release = "ALL";
		}

		// Execute the first search.
		ResponseEntity<String> responseEntity = restTemplate
				.exchange(
						url,
						HttpMethod.POST,
						new HttpEntity<String>(
								"quickFlag=true&edit=&sent=true&disponibility=TOUS&quickSearch=&title=&author="
										+ "&editor=&distributor=&collection=&serie=&codeInterne=&ean=&release="
										+ release
										+ "&catalogueFEL=true&_catalogueFEL=on&_catalogueHUB=on&_cataloguePNB=on"
										+ "&_scolaire=on&dateCreationIn=&dateCreationOut=&theme=&_codePrixLitteraire=1"
										+ "&_anneePrixLitteraire=1",
								this.generateHeaders("application/x-www-form-urlencoded")),
						String.class);

		this.checkForSearchError(responseEntity);

		this.initialized = true;
	}

	public Reference loadReferenceFromUrl(String url, boolean fullReference) {
		// Execute the call.
		ResponseEntity<String> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET,
				new HttpEntity<String>(this.generateHeaders(null)),
				String.class);

		Jerry document = Jerry.jerry(responseEntity.getBody());

		final Reference reference = new Reference();

		reference.setDilicomUrl(url);

		document.$(".detail_fiche:nth-child(1) tbody:nth-child(1) tr").each(
				new JerryFunction() {
					@Override
					public boolean onNode(Jerry $this, int index) {
						String tdContent = DilicomHttpUtil.cleanString($this.$(
								"td").html());
						if (index == 3
								&& $this.$("th").html().contains("EAN 13")) {
							reference.setEan13(tdContent);
						} else if (index == 7
								&& $this.$("th").html().contains("Titre")) {
							reference.setTitle(tdContent);
						} else if (index == 8
								&& $this.$("th").html()
										.contains("Libell&eacute; Standard")) {
							reference.setStandardLabel(tdContent);
						} else if (index == 9
								&& $this.$("th").html().contains("Auteur")) {
							reference.setAuthor(tdContent);
						} else if (index == 10
								&& $this.$("th")
										.html()
										.contains(
												"Collection abr&eacute;g&eacute;")) {
							reference.setShortCollection(tdContent);
						} else if (index == 11
								&& $this.$("th").html()
										.contains("Nom de l'&eacute;diteur")) {
							reference.setPublisherName(tdContent);
						} else if (index == 12
								&& $this.$("th").html()
										.contains("ISBN Editeur")) {
							reference.setIsbnOrShortTitle(tdContent);
						}

						return true;
					}
				});

		reference
				.setTheme(DilicomHttpUtil
						.cleanString(document
								.$(".detail_fiche:nth-child(1) tbody:nth-child(2) td.wrappable div")
								.html()));

		String publicationDate = DilicomHttpUtil.cleanString(document.$(
				".detail_fiche:nth-child(2) td:nth-child(3)").html());

		if (publicationDate != null && !publicationDate.startsWith("Pas paru")) {
			try {
				reference.setPublicationDate(new SimpleDateFormat(
						PUBLICATION_DATE_FORMAT).parse(publicationDate));
			} catch (NumberFormatException numberFormatEx) {
				logger.error(publicationDate + " could not be parsed as date "
						+ PUBLICATION_DATE_FORMAT, numberFormatEx);
			} catch (ParseException parseEx) {
				logger.error(publicationDate + " could not be parsed as date "
						+ PUBLICATION_DATE_FORMAT, parseEx);
			}
		}

		reference.setCoverImageUrl(document.$("a#cover_img").attr("href"));

		return reference;
	}

	private void loginToDilicom() {
		String url = "https://dilicom-prod.centprod.com/j_spring_security_check?j_username="
				+ env.getRequiredProperty("dilicom.username")
				+ "&j_password="
				+ env.getRequiredProperty("dilicom.password");

		// Execute the call.
		ResponseEntity<Void> responseEntity = this.restTemplate.postForEntity(
				url, null, Void.class);

		// Check for error
		if (responseEntity.getStatusCode() != HttpStatus.FOUND) {
			throw new RuntimeException(
					"Could not login to dilicom. Http Status : "
							+ responseEntity.getStatusCode());
		}

		jSessionId = "";
		tomcatid = "";
		for (String cookie : responseEntity.getHeaders().get("Set-Cookie")) {
			if (cookie.contains("JSESSIONID")) {
				jSessionId += cookie.split(";")[0];
			} else if (cookie.contains("TOMCATID")) {
				tomcatid += cookie.split(";")[0];
			}
		}

		logger.debug("Login to dilicom session id : " + jSessionId);
	}

	private void loginToDilifac() {
		String url = "https://dilicom-prod.centprod.com/dilifac/j_acegi_security_check";

		ResponseEntity<Void> responseEntity = this.restTemplate.postForEntity(
				url,
				"j_username=" + env.getRequiredProperty("dilicom.username")
						+ "&j_password="
						+ env.getRequiredProperty("dilicom.password"),
				Void.class);

		logger.debug("Login to dilifac response status : "
				+ responseEntity.getStatusCode());
	}

	private void logoutDilicom() {
		String url = "https://dilicom-prod.centprod.com/j_spring_security_logout";

		// Execute the call.
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<String>(this.generateHeaders()),
				Void.class);

		logger.debug("logout to dilicom response status : "
				+ responseEntity.getStatusCode());
	}

	private void openSearchPage() {
		String url = "https://dilicom-prod.centprod.com/catalogue/consulter_articles.html;"
				+ jSessionId + "?newsearch=true";

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url,
				HttpMethod.GET,
				new HttpEntity<String>(this
						.generateHeaders("application/x-www-form-urlencoded")),
				String.class);

		if (responseEntity.getStatusCode() != HttpStatus.OK
				&& !responseEntity
						.getBody()
						.contains(
								"<input "
										+ "type=\"submit\" "
										+ "value=\"RECHERCHER\" "
										+ "onclick=\"return valider('consultation');\" "
										+ "class=\"\">")) {

			throw new RuntimeException(
					"Open search page returned an invalid result.");
		}
	}

	public List<Reference> searchReferences(int page, boolean orderDesc,
			int pageSize) {
		if (!this.initialized) {
			throw new ConnectorNotInitializedException();
		}

		logger.info("Request page " + page);

		String url = "https://dilicom-prod.centprod.com/catalogue/consulter_articles.html?pageSize="
				+ pageSize
				+ "&date_parution&orderDesc="
				+ orderDesc
				+ "&page="
				+ page + "&newsearch=false";

		ResponseEntity<String> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<String>(this.generateHeaders()),
				String.class);

		this.checkForSearchError(responseEntity);

		Jerry document = Jerry.jerry(responseEntity.getBody());

		final List<Reference> references = new ArrayList<Reference>();

		document.$(".book_row").each(new JerryFunction() {
			@Override
			public boolean onNode(Jerry $this, int index) {
				Reference reference = loadReferenceFromUrl(
						"https://dilicom-prod.centprod.com"
								+ $this.$(".fiche_title a").attr("href"), false);

				references.add(reference);

				return true;
			}
		});

		return references;
	}

	public class ConnectorNotInitializedException extends RuntimeException {
		private static final long serialVersionUID = -2822541028506751842L;
	}
}
