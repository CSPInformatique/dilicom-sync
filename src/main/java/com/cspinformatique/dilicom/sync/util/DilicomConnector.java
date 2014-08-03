package com.cspinformatique.dilicom.sync.util;

import javax.annotation.Resource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@PropertySource("classpath:config/dilicom.properties")
public class DilicomConnector {

	@Resource
	private Environment env;

	private RestTemplate restTemplate;

	private String jSessionId;

	public DilicomConnector() {
		this.restTemplate = new RestTemplate();
	}

	private HttpHeaders generateHeaders(String referer, Integer contentLength, String contentType) {
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Host", "dilicom-prod.centprod.com");
		headers.add("Connection", "keep-alive");
		if (contentLength != null) {
			headers.add("Content-Length", String.valueOf(contentLength));
		}
		if(contentType != null){
			headers.add("Content-Type", contentType);
		}
		headers.add("Referer", referer);
		headers.add("Cache-Control", "max-age=0");
		headers.add("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.add(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
		headers.add("Accept-Language",
				"fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4,zh-CN;q=0.2");
		headers.add("Accept-Encoding", "gzip,deflate,sdch");
		headers.add("Origin", "https://dilicom-prod.centprod.com");
		headers.add("COOKIE", "TOMCATID=TomcatServers.alphaweb1; " + jSessionId
				+ ";");

		return headers;
	}

	public void loginToDilicom() {
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
		for (String cookie : responseEntity.getHeaders().get("Set-Cookie")) {
			if (cookie.contains("JSESSIONID")) {
				jSessionId += cookie.split(";")[0];
			}
		}
	}

	public String searchNewReferences(int page) {
		// int resultPerPage = 30;

		String url = "https://dilicom-prod.centprod.com/catalogue/consulter_articles.html?newsearch=true";

		// Execute the first search.
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url,
				HttpMethod.GET,
				new HttpEntity<Void>(this.generateHeaders(
						"https://dilicom-prod.centprod.com/index.html", null, null)),
				String.class);

		url = "https://dilicom-prod.centprod.com/catalogue/consulter_articles.html?page=1&newsearch=false";

		// Execute the first search.
		responseEntity = restTemplate
				.exchange(
						url,
						HttpMethod.POST,
						new HttpEntity<String>(
								"quickFlag=true&edit=&sent=true&disponibility=TOUS&quickSearch=&title="
										+ "&author=&editor=&distributor=&collection=&serie=&codeInterne=&ean=&"
										+ "release=ONE_MONTH&catalogueFEL=true&_catalogueFEL=on&_catalogueHUB=on"
										+ "&_cataloguePNB=on&_scolaire=on&dateCreationIn=&dateCreationOut=&theme="
										+ "&_codePrixLitteraire=1&_anneePrixLitteraire=1",
								this.generateHeaders("https://dilicom-prod.centprod.com/catalogue/consulter_articles.html?newsearch=true", 350, "application/x-www-form-urlencoded")),
						String.class);

		// Check for error
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			throw new RuntimeException(
					"Error while requesting new references : "
							+ responseEntity.getStatusCode());
		}

		return responseEntity.getBody();
	}
}
