package com.cspinformatique.dilicom.sync.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cspinformatique.dilicom.sync.entity.Publisher;

public interface PublisherService {
	public void addPublisherToConfiguration(Publisher publisher);
	
	public void deletePublisherFromConfiguration(String name);
	
	public Page<Publisher> searchPublisher(String searchString, Pageable pageable);
	
	public Publisher findByName(String name);
	
	public List<Publisher> findConfiguredPublisher();
}
