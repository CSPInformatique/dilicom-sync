package com.cspinformatique.dilicom.sync.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cspinformatique.dilicom.sync.entity.ConfigEntry;
import com.cspinformatique.dilicom.sync.entity.ConfigEntry.Name;
import com.cspinformatique.dilicom.sync.repository.mongo.ConfigEntryRepository;
import com.cspinformatique.dilicom.sync.service.ConfigEntryService;

@Service
@PropertySource("classpath:config/defaultConfigEntries.properties")
public class ConfigEntryServiceImpl implements ConfigEntryService {
	@Resource
	private Environment env;
	
	@Autowired
	private ConfigEntryRepository configEntryRepository;
	
	private Map<Name, ConfigEntry> cachedConfigEntries;
	
	public ConfigEntryServiceImpl() {
		this.cachedConfigEntries = new HashMap<Name, ConfigEntry>();
	}
	
	@Override
	public String findConfigEntry(Name name) {
		ConfigEntry configEntry = this.cachedConfigEntries.get(name);
				
		if(configEntry == null){
			configEntry = this.configEntryRepository.findOne(name);
		}
		
		if(configEntry != null){
			return configEntry.getValue();
		}else{
			return null;
		}
	}
	
	@PostConstruct
	private void init(){
		this.initializeDefaultConfig();
	}
	
	private void initializeDefaultConfig(){
		for(Name name : Name.values()){
			String value = env.getProperty(name.toString());
			if(value != null && this.configEntryRepository.findOne(name) == null){
				this.save(new ConfigEntry(name, value));
			}
		}
	}

	@Override
	public ConfigEntry save(ConfigEntry configEntry) {
		configEntry = this.configEntryRepository.save(configEntry);
		
		cachedConfigEntries.put(configEntry.getName(), configEntry);
		
		return configEntry;
	}

}
