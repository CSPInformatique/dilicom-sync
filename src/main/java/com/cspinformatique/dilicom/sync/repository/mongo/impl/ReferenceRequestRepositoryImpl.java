package com.cspinformatique.dilicom.sync.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cspinformatique.dilicom.sync.entity.ReferenceRequest;
import com.cspinformatique.dilicom.sync.entity.ReferenceRequest.Status;
import com.cspinformatique.dilicom.sync.repository.mongo.ReferenceRequestRepositoryCustom;

public class ReferenceRequestRepositoryImpl implements
		ReferenceRequestRepositoryCustom {
	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public Integer findLastPageIndexProcessed() {
		return mongoOperation.findOne(
				new Query(new Criteria("status").is(Status.OK)).limit(1).with(
						new Sort(Sort.Direction.DESC, "pageIndex")),
				ReferenceRequest.class).getPageIndex();
	}

	@Override
	public Integer findOldestPageIndexToProcess() {
		ReferenceRequest referenceRequest = mongoOperation.findOne(
				new Query(new Criteria("status").is(Status.TO_PROCESS))
						.limit(1).with(
								new Sort(Sort.Direction.ASC, "pageIndex")),
				ReferenceRequest.class);

		if (referenceRequest == null) {
			return null;
		}

		return referenceRequest.getPageIndex();
	}

}
