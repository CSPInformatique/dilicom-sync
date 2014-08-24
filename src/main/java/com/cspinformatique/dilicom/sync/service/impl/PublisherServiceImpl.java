package com.cspinformatique.dilicom.sync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.Publisher;
import com.cspinformatique.dilicom.sync.repository.elasticsearch.PublisherElasticRepository;
import com.cspinformatique.dilicom.sync.repository.redis.PublisherRedisRepository;
import com.cspinformatique.dilicom.sync.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {
	@Autowired private PublisherElasticRepository publisherElasticRepository;
	@Autowired private PublisherRedisRepository publisherRedisRepository;
	
	@Override
	public void addPublisherToConfiguration(Publisher publisher){
		this.publisherRedisRepository.savePublisher(publisher);
	}
	
	@Override
	public void deletePublisherFromConfiguration(String name){
		this.publisherRedisRepository.deletePublisher(name);
	}
	
	@Override
	public Page<Publisher> searchPublisher(String searchString,
			Pageable pageable) {
		return this.publisherElasticRepository.findByNameLike(searchString, pageable);
	}

	@Override
	public Publisher findByName(String name) {
		return this.publisherElasticRepository.findOne(name);
	}

	@Override
	public List<Publisher> findConfiguredPublisher() {
		return this.publisherRedisRepository.findAll();
	}
}