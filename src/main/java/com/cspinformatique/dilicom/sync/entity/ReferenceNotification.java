package com.cspinformatique.dilicom.sync.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class ReferenceNotification {
	public static final String STATUS_ERROR = "ERROR";
	public static final String STATUS_OK = "OK";
	
	private String ean13;
	private Date timestamp;
	private String status;
	private String cause;
	
	public ReferenceNotification(){

	}

	public ReferenceNotification(String ean13, Date timestamp,
			String status, String cause) {
		this.ean13 = ean13;
		this.timestamp = timestamp;
		this.status = status;
	}

	@Id
	public String getEan13() {
		return ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
}
