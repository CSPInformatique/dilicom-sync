package com.cspinformatique.dilicom.sync.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;
import com.cspinformatique.dilicom.sync.entity.ReferenceNotification.Status;
import com.cspinformatique.dilicom.sync.repository.mongo.ReferenceNotificationRepositoryCustom;

public class ReferenceNotificationRepositoryImpl implements
		ReferenceNotificationRepositoryCustom {
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public ReferenceNotification findNextToProcess() {
		return mongoOperations.findOne(
				new Query(new Criteria("status").is(Status.TO_PROCESS)).with(
						new Sort(Direction.ASC, "timestamp")).limit(1),
				ReferenceNotification.class);
	}
}
