package com.cspinformatique.dilicom.sync.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class ReferenceController {
	
	@RequestMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void test(){
		System.out.println("Controller working properly");
	}
}
