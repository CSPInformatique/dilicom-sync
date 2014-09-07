package com.cspinformatique.dilicom.sync.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="configEntry")
public class ConfigEntry {
	public enum Name{
		PAGE_REQUEST_MAX_THREADS,
		REFERENCES_MAX_THREADS
	}
	
	private Name name;
	private String value;
	
	public ConfigEntry(){
		
	}
	
	public ConfigEntry(Name name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Id
	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
