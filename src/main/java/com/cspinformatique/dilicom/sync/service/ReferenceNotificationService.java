package com.cspinformatique.dilicom.sync.service;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;

public interface ReferenceNotificationService {
	public long count();
	
	public ReferenceNotification findNextReferenceToProcess();
	
	public ReferenceNotification save(ReferenceNotification referenceNotification);
	
	public Iterable<ReferenceNotification> saveAll(Iterable<ReferenceNotification> referenceNotifications);
}
