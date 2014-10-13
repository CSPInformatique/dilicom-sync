package com.cspinformatique.dilicom.sync.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceRepository extends ReferenceRepositoryCustom,
		ElasticsearchCrudRepository<Reference, String> {
	
}
