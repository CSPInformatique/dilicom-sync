package com.cspinformatique.dilicom.sync.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="referenceRequest")
public class ReferenceRequest {
	public enum Status{
		ERROR, TO_PROCESS, OK
	}
	
	public Integer pageIndex;
	private Status status;
	private String cause;
	
	public ReferenceRequest(){
		
	}

	public ReferenceRequest(int pageIndex, Status status, String cause) {
		this.pageIndex = pageIndex;
		this.status = status;
		this.cause = cause;
	}

	@Id
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
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
