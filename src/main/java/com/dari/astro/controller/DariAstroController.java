package com.dari.astro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dari.astro.bos.SignUpUser;
import com.dari.astro.service.DariAstroService;
import com.dari.astro.utils.ResultResponse;

@RestController
@RequestMapping("/dari_astro")
public class DariAstroController {
	
	@Autowired
	private DariAstroService dariAstroService;
	
	@RequestMapping(value = "/sayHello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse signUpContact() {
		ResultResponse name= new ResultResponse();
		name.setResult("Srinivas");
		return name;
	}
	
	@RequestMapping(value = "/signUpUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse signUpContact(@RequestBody SignUpUser signUpUser) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = dariAstroService.signUpUser(signUpUser);
		return resultResponse;
	}

}
