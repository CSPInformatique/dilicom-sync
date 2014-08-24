package com.cspinformatique.dilicom.sync.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="publisher")
public class Publisher {
	private String name;
	
	public Publisher(String name){
		this.name = name;
	}

	@Id
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
