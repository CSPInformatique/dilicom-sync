package com.cspinformatique.dilicom.sync.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.ReferenceRequest;
import com.cspinformatique.dilicom.sync.repository.mongo.ReferenceRequestRepository;
import com.cspinformatique.dilicom.sync.service.ReferenceRequestService;

@Service
public class ReferenceRequestServiceImpl implements
		ReferenceRequestService {
	@Autowired private ReferenceRequestRepository referenceRequestRepository;
	
	@Override
	public int findPageNextPageToProcess(){
		Integer nextPageToProcess = this.referenceRequestRepository.findOldestPageIndexToProcess();
		
		if(nextPageToProcess == null){
			nextPageToProcess = this.referenceRequestRepository.findOldestPageIndexToProcess();
			
			if (nextPageToProcess == null){
				return 0;
			}
		}
		
		return nextPageToProcess;
	}
	
	@Override
	public void save(ReferenceRequest referenceRequest) {
		referenceRequestRepository.save(referenceRequest);
	}

}
