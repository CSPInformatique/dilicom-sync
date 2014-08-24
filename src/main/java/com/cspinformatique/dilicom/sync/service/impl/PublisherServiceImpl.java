package com.cspinformatique.dilicom.sync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.Publisher;
import com.cspinformatique.dilicom.sync.repository.mongo.PublisherRepository;
import com.cspinformatique.dilicom.sync.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {
	@Autowired private PublisherRepository publisherRepository;
	
	@Override
	public void addPublisherToConfiguration(Publisher publisher){
		this.publisherRepository.save(publisher);
	}
	
	@Override
	public void deletePublisherFromConfiguration(String name){
		this.publisherRepository.delete(name);
	}
	
	@Override
	public Page<Publisher> searchPublisher(String searchString,
			Pageable pageable) {
		return this.publisherRepository.findByNameLike(searchString, pageable);
	}

	@Override
	public Publisher findByName(String name) {
		return this.publisherRepository.findOne(name);
	}

	@Override
	public List<Publisher> findConfiguredPublisher() {
		return this.publisherRepository.findAll();
	}
}