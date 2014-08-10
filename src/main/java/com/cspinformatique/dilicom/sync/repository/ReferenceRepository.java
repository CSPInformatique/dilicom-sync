package com.cspinformatique.dilicom.sync.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceRepository extends
		ElasticsearchCrudRepository<Reference, String> {
	public Page<Reference> findAll(Pageable pageable);

	public Page<Reference> findByLoadedIntoErp(boolean loadedIntoErp, Pageable pageable);
	
	public Page<Reference> findByTitleLike(String title, Pageable pageable);

	public Page<Reference> findByTitleLikeAndLoadedIntoErp (String title, boolean loadedIntoErp,
			Pageable pageable);
}
