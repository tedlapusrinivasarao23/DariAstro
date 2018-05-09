package com.dari.astro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dari.astro.bos.BirthChartDetails;
import com.dari.astro.bos.SignUpUser;
import com.dari.astro.service.DariAstroService;
import com.dari.astro.utils.AddMultipleBirthChartDetails;
import com.dari.astro.utils.BirthChartResultResponse;
import com.dari.astro.utils.ForgotPassword;
import com.dari.astro.utils.LoginResponse;
import com.dari.astro.utils.LoginUser;
import com.dari.astro.utils.LogoutResponse;
import com.dari.astro.utils.LogoutUser;
import com.dari.astro.utils.ResultResponse;
import com.dari.astro.utils.UpdatePassword;

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
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LoginResponse loginUser(@RequestBody LoginUser loginUser) {
		LoginResponse loginResponse = dariAstroService.loginUser(loginUser);
		return loginResponse;
	}
	
	@RequestMapping(value = "/logoutUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LogoutResponse logoutUser(@RequestBody LogoutUser logoutUser) {
		LogoutResponse logoutResponse = dariAstroService.logoutContact(logoutUser);
		return logoutResponse;
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse updatePassword(@RequestBody UpdatePassword updatePassword) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = dariAstroService.updatePassword(updatePassword);
		return resultResponse;
	}
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse forgotPassword(@RequestBody ForgotPassword forgotPassword) {
		ResultResponse resultResponse = dariAstroService.forgotPassword(forgotPassword);
		return resultResponse;
	}
	
	@RequestMapping(value = "/profileUpdatation", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse profileUpdatation(@RequestBody SignUpUser signUpUser){
		ResultResponse resultResponse=dariAstroService.profileUpdatation(signUpUser);
		return resultResponse;
	}
	
	@RequestMapping(value = "/addBirthChart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartResultResponse addBirthChart(@RequestBody BirthChartDetails birthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		birthChartResultResponse = dariAstroService.addBirthChart(birthChartDetails);
		return birthChartResultResponse;
	}
	
	@RequestMapping(value = "/addMultipleBirthChart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartResultResponse addMultipleBirthChart(@RequestBody AddMultipleBirthChartDetails addMultipleBirthChartDetails){
		BirthChartResultResponse birthChartResultResponse=dariAstroService.addMultipleBirthChart(addMultipleBirthChartDetails);
		return birthChartResultResponse;
	}

}
