package com.cspinformatique.dilicom.sync.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.dilicom.sync.entity.Publisher;
import com.cspinformatique.dilicom.sync.service.PublisherService;

@Controller
@RequestMapping("/publisher")
public class PublisherController {
	@Autowired private PublisherService publisherService; 
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method=RequestMethod.PUT)
	public void addPublisherToConfiguration(@RequestBody Publisher publisher){
		this.publisherService.addPublisherToConfiguration(publisher);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method=RequestMethod.DELETE, value="/name")
	public void deletePublisherFromConfiguration(@PathVariable String name){
		this.publisherService.deletePublisherFromConfiguration(name);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Publisher> findConfiguredPublishers(){
		return this.publisherService.findConfiguredPublisher();
	}
	
	@RequestMapping(method=RequestMethod.GET, params="searchString")
	public @ResponseBody Page<Publisher> searchPublishers(@RequestParam String searchString){
		return this.publisherService.searchPublisher(searchString, new PageRequest(0, 20));
	}
	
	
}
