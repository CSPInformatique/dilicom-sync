package com.cspinformatique.dilicom.sync.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;
import com.cspinformatique.dilicom.sync.repository.mongo.ReferenceNotificationRepository;
import com.cspinformatique.dilicom.sync.service.ReferenceNotificationService;

@Service
public class ReferenceNotificationServiceImpl implements
		ReferenceNotificationService {
	
	@Autowired
	private ReferenceNotificationRepository referenceNotificationRepository;

	@Override
	public ReferenceNotification findNextReferenceToProcess(){
		return this.referenceNotificationRepository.findNextToProcess();
	}
	
	@Override
	public long count(){
		return this.referenceNotificationRepository.count();
	}
	
	@Override
	public ReferenceNotification save(ReferenceNotification referenceNotification){
		return this.referenceNotificationRepository.save(referenceNotification);
	}
	
	@Override
	public Iterable<ReferenceNotification> saveAll(
			Iterable<ReferenceNotification> referenceNotifications) {
		
		return this.referenceNotificationRepository
				.save(referenceNotifications);
	}

}
