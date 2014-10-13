package com.cspinformatique.dilicom.sync.repository.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceRepositoryCustom {
	public Page<Reference> search(String searchQuery, String[] fields, Boolean hided, Boolean loadedIntoErp, Pageable pageable);
}
