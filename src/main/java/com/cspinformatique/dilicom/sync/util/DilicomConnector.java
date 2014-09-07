package com.cspinformatique.dilicom.sync.util;

import java.util.List;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface DilicomConnector {

	public void initializeSearch(boolean fullLoad);

	public Reference loadReferenceFromUrl(String url);

	public List<String> searchReferences(int page, boolean orderDesc, int pageSize);
}
