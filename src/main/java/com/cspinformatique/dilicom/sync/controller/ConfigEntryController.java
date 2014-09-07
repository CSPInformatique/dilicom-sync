package com.cspinformatique.dilicom.sync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.dilicom.sync.entity.ConfigEntry;
import com.cspinformatique.dilicom.sync.entity.ConfigEntry.Name;
import com.cspinformatique.dilicom.sync.service.ConfigEntryService;

@Controller
@RequestMapping("/configEntry")
public class ConfigEntryController {
	@Autowired private ConfigEntryService configEntryService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, value="/{name}")
	public @ResponseBody String getConfigEntry(@PathVariable String name){
		return this.configEntryService.findConfigEntry(Name.valueOf(name));
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.PUT)
	public @ResponseBody ConfigEntry saveConfigEntry(@RequestBody ConfigEntry configEntry){
		return this.configEntryService.save(configEntry);
	}
}
