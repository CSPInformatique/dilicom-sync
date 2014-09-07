package com.cspinformatique.dilicom.sync.repository.mongo;

import org.springframework.data.repository.CrudRepository;

import com.cspinformatique.dilicom.sync.entity.ConfigEntry;
import com.cspinformatique.dilicom.sync.entity.ConfigEntry.Name;

public interface ConfigEntryRepository extends CrudRepository<ConfigEntry, Name>{

}
