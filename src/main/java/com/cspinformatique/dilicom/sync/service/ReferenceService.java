package com.cspinformatique.dilicom.sync.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceService {

	public void exportDump();
	
	public void hideReference(String ean13);
	
	public void importDump();
	
	public void initializeReferencesIndex();
	
	public void initializeReferencesIndex(int startPageIndex);
	
	public Reference loadReferenceFromDilicomUrl(String dilicomUrl);
	
	public void loadLatestReferences();
	
	public void publishToOdoo(String ean13);
	
	public void save(Reference reference);
	
	public void save(Iterable<Reference> references);
	
	public Page<Reference> search(Pageable pageable);
	
	public Page<Reference> search(boolean loadedIntoErp, Pageable pageable);
	
	public Page<Reference> searchByHided(boolean hided, Pageable pageable);
	
	public Page<Reference> search(boolean hided, boolean loadedIntoErp, Pageable pageable);

	public Page<Reference> searchByTitle(String title, Pageable pageable);
	
	public Page<Reference> searchByTitle(String title, boolean loadedIntoErp, Pageable pageable);
		
	public Page<Reference> searchByTitle(String title, boolean hided, boolean loadedIntoErp, Pageable pageable);
	
	public Page<Reference> searchByTitleAndHided(String title, boolean hided, Pageable pageable);
	
	public void unhideReference(String ean13);
	
}
