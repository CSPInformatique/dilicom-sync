package com.cspinformatique.dilicom.sync.repository.mongo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cspinformatique.dilicom.sync.entity.ReferenceRequest;

public interface ReferenceRequestRepository extends
		PagingAndSortingRepository<ReferenceRequest, Integer>, ReferenceRequestRepositoryCustom {

}
