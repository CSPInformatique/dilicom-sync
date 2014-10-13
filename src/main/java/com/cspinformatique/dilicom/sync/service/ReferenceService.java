package com.cspinformatique.dilicom.sync.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceService {

	public void exportDump();
	
	public Reference findOne(String ean13);
	
	public void hideReference(String ean13);
	
	public void importDump();
	
	public Reference loadReferenceFromDilicomUrl(String dilicomUrl);
		
	public void publishToErp(String ean13);
	
	public void save(Reference reference);
	
	public void save(Iterable<Reference> references);
	
	public Page<Reference> search(String query, String[] fields, Boolean hided, Boolean loadedIntoErp, Pageable pageable);
	
	public void unhideReference(String ean13);
	
}
