package com.cspinformatique.dilicom.sync.repository.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.cspinformatique.dilicom.sync.entity.Publisher;

public interface PublisherElasticRepository extends
		ElasticsearchCrudRepository<Publisher, String> {

	public Page<Publisher> findByNameLike(String name, Pageable pageable);
}
