package com.cspinformatique.dilicom.sync.repository.redis.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;
import com.cspinformatique.dilicom.sync.repository.redis.ReferenceNotificationRepository;

@Repository
public class ReferenceNotificationRepositoryImpl implements
		ReferenceNotificationRepository {
//	private static final String REFERENCE_NOTIFICATION_KEY = "referenceNotification";
	
	@Autowired RedisTemplate<String, ReferenceNotification> referenceNotificationRepository;
	
	@Resource(type=RedisTemplate.class)
	private BoundHashOperations<String, String, ReferenceNotification> hashOps;
	
	@Override
	public List<ReferenceNotification> findAll() {
		return this.hashOps.values();
	}

	@Override
	public void putReferenceNotification(
			ReferenceNotification referenceNotification) {
		// TODO Auto-generated method stub

	}

}
