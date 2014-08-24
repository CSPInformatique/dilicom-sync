package com.cspinformatique.dilicom.sync.repository.redis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cspinformatique.dilicom.sync.entity.Publisher;
import com.cspinformatique.dilicom.sync.repository.redis.PublisherRedisRepository;

@Repository
public class PublisherRedisRepositoryImpl implements PublisherRedisRepository {
	public static final String PUBLISHER_KEY = "publisher";
	@Autowired
	private RedisTemplate<String, Publisher> publisherRedisTemplate;

	@Override
	public void deletePublisher(String name) {
		this.publisherRedisTemplate.boundHashOps(PUBLISHER_KEY).delete(name);
	}
	
	@Override
	public List<Publisher> findAll(){
		List<Publisher> publishers = new ArrayList<Publisher>();
		
		for(Object object : this.publisherRedisTemplate.boundHashOps(PUBLISHER_KEY).values()){
			publishers.add((Publisher)object);
		}
		
		return publishers;
	}

	@Override
	public void savePublisher(Publisher publisher) {
		this.publisherRedisTemplate.boundHashOps(PUBLISHER_KEY).put(
				publisher.getName(), publisher);
	}
}
