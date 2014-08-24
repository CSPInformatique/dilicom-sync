package com.cspinformatique.dilicom.sync.repository.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceRepository extends
		ElasticsearchCrudRepository<Reference, String> {
	public Page<Reference> findAll(Pageable pageable);
	
	public Page<Reference> findByHided(boolean hided, Pageable pageable);

	public Page<Reference> findByHidedAndLoadedIntoErp(boolean hided, boolean loadedIntoErp, Pageable pageable);
	
	public Page<Reference> findByLoadedIntoErp(boolean loadedIntoErp, Pageable pageable);
	

	public Page<Reference> findByTitleLike(String title, Pageable pageable);
	
	public Page<Reference> findByTitleLikeAndHided(String title, boolean hided, Pageable pageable);

	public Page<Reference> findByTitleLikeAndLoadedIntoErp(String title, boolean loadedIntoErp,
			Pageable pageable);
	
	public Page<Reference> findByTitleLikeAndHidedAndLoadedIntoErp (String title, boolean hided, boolean loadedIntoErp,
			Pageable pageable);
}
