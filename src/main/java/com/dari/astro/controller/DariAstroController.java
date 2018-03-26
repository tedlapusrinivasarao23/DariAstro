package com.dari.astro.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dari.astro.utils.ResultResponse;

@RestController
@RequestMapping("/dari_astro")
public class DariAstroController {
	
	@RequestMapping(value = "/sayHello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse signUpContact() {
		ResultResponse name= new ResultResponse();
		name.setResult("Srinivas");
		return name;
	}

}
