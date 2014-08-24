package com.cspinformatique.dilicom.sync.repository.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cspinformatique.dilicom.sync.entity.Publisher;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher, String>{	
	public List<Publisher> findAll();
	
	public Page<Publisher> findByNameLike(String searchString, Pageable pageable);
}
