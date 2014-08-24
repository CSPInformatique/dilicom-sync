package com.cspinformatique.dilicom.sync.repository.redis;

import java.util.List;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;

public interface ReferenceNotificationRepository {
	public List<ReferenceNotification> findAll();
	
	public void putReferenceNotification(ReferenceNotification referenceNotification);
}
