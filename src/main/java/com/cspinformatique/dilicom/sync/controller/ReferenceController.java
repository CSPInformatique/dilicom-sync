package com.cspinformatique.dilicom.sync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.dilicom.sync.entity.Reference;
import com.cspinformatique.dilicom.sync.service.ReferenceService;

@Controller
@RequestMapping("/reference")
public class ReferenceController {
	@Autowired private ReferenceService referenceService;

	@RequestMapping(params="export")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportDeump(){
		this.referenceService.exportDump();
	}

	@RequestMapping(params="import")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void importDump(){
		this.referenceService.importDump();
	}
	
	@RequestMapping(params="initialize")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void initializeReferencesIndex(){
		this.referenceService.initializeReferencesIndex();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(params={"initialize", "startPageIndex"})
	public void initializeReferencesIndex(@RequestParam int startPageIndex){
		this.referenceService.initializeReferencesIndex(startPageIndex);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Page<Reference> search(int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), "publicationDate"));
		
		return this.referenceService.search(pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="loadedIntoErp")
	public @ResponseBody Page<Reference> search(boolean loadedIntoErp, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), "publicationDate"));
		
		return this.referenceService.search(loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="title")
	public @ResponseBody Page<Reference> search(String title, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), "publicationDate"));
		
		return this.referenceService.searchByTitle(title, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "loadedIntoErp"})
	public @ResponseBody Page<Reference> search(String title, boolean loadedIntoErp, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), "publicationDate"));
		
		return this.referenceService.searchByTitle(title, loadedIntoErp, pageRequest);
	}
}
