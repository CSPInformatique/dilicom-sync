package com.cspinformatique.dilicom.sync.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.cspinformatique.dilicom.sync.entity.Reference;

public interface ReferenceRepository extends ElasticsearchCrudRepository<Reference, String> {

}
