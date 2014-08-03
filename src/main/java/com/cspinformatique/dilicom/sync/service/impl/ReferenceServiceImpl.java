package com.cspinformatique.dilicom.sync.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.Reference;
import com.cspinformatique.dilicom.sync.repository.ReferenceRepository;
import com.cspinformatique.dilicom.sync.service.ReferenceService;

@Service
public class ReferenceServiceImpl implements ReferenceService {
	@Autowired private ReferenceRepository referenceRepository;
	
	@Override
	public Reference loadReferenceFromUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void loadReferencesFromDilicom(){
		// Login to Dilicom.com
	}

}
