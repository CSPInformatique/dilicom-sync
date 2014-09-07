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

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"hided", "loadedIntoErp"})
	public @ResponseBody Page<Reference> search(boolean hided, boolean loadedIntoErp, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.search(hided, loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"hided", "loadedIntoErp", "orderBy"})
	public @ResponseBody Page<Reference> search(boolean hided, boolean loadedIntoErp, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.search(hided, loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="loadedIntoErp")
	public @ResponseBody Page<Reference> search(boolean loadedIntoErp, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.search(loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"loadedIntoErp", "orderBy"})
	public @ResponseBody Page<Reference> search(boolean loadedIntoErp, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.search(loadedIntoErp, pageRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Page<Reference> search(int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.search(pageRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="orderBy")
	public @ResponseBody Page<Reference> search(int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.search(pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "hided", "loadedIntoErp", "orderBy"})
	public @ResponseBody Page<Reference> search(String title, boolean hided, boolean loadedIntoErp, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.searchByTitle(title, hided, loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="title")
	public @ResponseBody Page<Reference> search(String title, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.searchByTitle(title, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "orderBy"})
	public @ResponseBody Page<Reference> search(String title, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.searchByTitle(title, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "loadedIntoErp"})
	public @ResponseBody Page<Reference> search(String title, boolean loadedIntoErp, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.searchByTitle(title, loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "hided", "loadedIntoErp"})
	public @ResponseBody Page<Reference> search(String title, boolean hided, boolean loadedIntoErp, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.searchByTitle(title, hided, loadedIntoErp, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "loadedIntoErp", "orderBy"})
	public @ResponseBody Page<Reference> search(String title, boolean loadedIntoErp, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.searchByTitle(title, loadedIntoErp, pageRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"hided", "orderBy"})
	public @ResponseBody Page<Reference> searchByHided(boolean hided, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.search(hided, pageRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params="hided")
	public @ResponseBody Page<Reference> searchByHided(boolean hided, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.search(hided, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "hided"})
	public @ResponseBody Page<Reference> searchByHided(String title, boolean hided, int page, int resultPerPage, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), DEFAULT_ORDER_BY));
		
		return this.referenceService.searchByTitle(title, hided, pageRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET, params={"title", "hided", "orderBy"})
	public @ResponseBody Page<Reference> searchByHided(String title, boolean hided, int page, int resultPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, resultPerPage, new Sort(Direction.valueOf(direction), orderBy));
		
		return this.referenceService.searchByTitleAndHided(title, hided, pageRequest);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value="/{ean13}", params="unhide")
	public void unhideReference(@PathVariable String ean13){
		this.referenceService.unhideReference(ean13);
	}
}
