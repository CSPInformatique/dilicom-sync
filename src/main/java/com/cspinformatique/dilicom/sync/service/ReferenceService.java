package com.cspinformatique.dilicom.sync.service;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceService {
	public Reference loadReferenceFromUrl(String url);
	
	public void loadReferencesFromDilicom();
}
