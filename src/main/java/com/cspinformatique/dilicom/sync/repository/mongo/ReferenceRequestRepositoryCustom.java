package com.cspinformatique.dilicom.sync.repository.mongo;

public interface ReferenceRequestRepositoryCustom {	
	public Integer findLastPageIndexProcessed();

	public Integer findOldestPageIndexToProcess();
}
