package com.cspinformatique.dilicom.sync.repository.redis;

import java.util.List;

import com.cspinformatique.dilicom.sync.entity.Publisher;

public interface PublisherRedisRepository {
	public void deletePublisher(String name);
	
	public List<Publisher> findAll();
	
	public void savePublisher(Publisher publisher);
}
