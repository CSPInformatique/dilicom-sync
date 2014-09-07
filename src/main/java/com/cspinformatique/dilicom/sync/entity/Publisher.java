package com.cspinformatique.dilicom.sync.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="publisher")
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
