package com.cspinformatique.dilicom.sync.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="referenceNotification")
public class ReferenceNotification {
	public enum Status{
		ERROR, TO_PROCESS, OK, PROCESSING
	}
	
	private String url;
	private Date timestamp;
	private Status status;
	private String cause;
	
	public ReferenceNotification(){

	}

	public ReferenceNotification(String url, Date timestamp,
			Status status, String cause) {
		this.url = url;
		this.timestamp = timestamp;
		this.status = status;
	}

	@Id
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
}
