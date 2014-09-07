package com.cspinformatique.dilicom.sync.service;

import com.cspinformatique.dilicom.sync.entity.ConfigEntry;
import com.cspinformatique.dilicom.sync.entity.ConfigEntry.Name;

public interface ConfigEntryService {
	public String findConfigEntry(Name name);
	
	public ConfigEntry save(ConfigEntry configEntry);
}
