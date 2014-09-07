package com.cspinformatique.dilicom.sync.repository.mongo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;

public interface ReferenceNotificationRepository extends
		PagingAndSortingRepository<ReferenceNotification, String>,
		ReferenceNotificationRepositoryCustom {

}
