package com.cspinformatique.dilicom.sync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cspinformatique.dilicom.sync.entity.Reference;
import com.cspinformatique.dilicom.sync.service.ReferenceService;

@Controller
@RequestMapping("/reference")
public class ReferenceController {
	private static final String DEFAULT_ORDER_BY = "publicationDate";
	
	@Autowired private ReferenceService referenceService;

	@RequestMapping(params="export")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportDeump(){
		this.referenceService.exportDump();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{ean13}", params="hide")
	public void hideReference(@PathVariable String ean13){
		this.referenceService.hideReference(ean13);
	}

	@RequestMapping(params="import")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void importDump(){
		this.referenceService.importDump();
	}
	
	@RequestMapping(params="publishToErp")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void publishReferenceToErp(String ean13){
		this.referenceService.publishToErp(ean13);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Page<Reference> search(Boolean hided, Boolean loadedIntoErp, int page, int resultPerPage, String sortBy, String direction){		
		return this.search(null, null, hided, loadedIntoErp, page, resultPerPage, sortBy, direction);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="query")
	public @ResponseBody Page<Reference> search(String query, Boolean hided, Boolean loadedIntoErp, int page, int resultPerPage, String sortBy, String direction){		
		return this.search(query, null, hided, loadedIntoErp, page, resultPerPage, sortBy, direction);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"query", "fields"})
	public @ResponseBody Page<Reference> search(String query, String[] fields, Boolean hided, Boolean loadedIntoErp, Integer page, Integer resultPerPage, String sortBy, String direction){
		if(page == null) page = 0;
		if(resultPerPage == null) resultPerPage = 50;
		if(sortBy == null) sortBy = DEFAULT_ORDER_BY;
		if(direction == null) direction = "ASC";	
		if(query != null && fields == null){
			fields = new String[]{"ean13", "title", "author", "publisherName"};
		}
		
		if(sortBy.equals("title") || sortBy.equals("author") || sortBy.equals("publisherName")){
			sortBy += ".raw";
		}
		
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), sortBy));
		
		return this.referenceService.search(query, fields, hided, loadedIntoErp, pageRequest);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{ean13}", params="unhide")
	public void unhideReference(@PathVariable String ean13){
		this.referenceService.unhideReference(ean13);
	}
}
