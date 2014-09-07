package com.cspinformatique.dilicom.sync.repository.mongo;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;

public interface ReferenceNotificationRepositoryCustom {
//	@Query("SELECT ref FROM ReferenceNotification ref, (SELECT minRef.url, MIN(minRef.timestamp) FROM ReferenceNotification minRef WHERE minRef.status = 'TO_PROCESS') nextRef WHERE ref.url = nextRef.url")
	public ReferenceNotification findNextToProcess();
}
