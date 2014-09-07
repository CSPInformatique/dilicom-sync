package com.cspinformatique.dilicom.sync.service;

import com.cspinformatique.dilicom.sync.entity.ReferenceRequest;

public interface ReferenceRequestService {
	public int findPageNextPageToProcess();
	
	public void save(ReferenceRequest referenceRequest);
}
