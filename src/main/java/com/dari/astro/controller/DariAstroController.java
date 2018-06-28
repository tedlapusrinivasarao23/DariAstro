package com.dari.astro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.dari.astro.utils.DeleteMultipleBirthCharts;
import com.dari.astro.utils.ForgotPassword;
import com.dari.astro.utils.LoginResponse;
import com.dari.astro.utils.LoginUser;
import com.dari.astro.utils.LogoutResponse;
import com.dari.astro.utils.LogoutUser;
import com.dari.astro.utils.ResultResponse;
import com.dari.astro.utils.TransactionBean;
import com.dari.astro.utils.TransactionResponse;
import com.dari.astro.utils.UpdatePassword;

@RestController
@RequestMapping("/dari_astro")
public class DariAstroController {

	@Autowired
	private DariAstroService dariAstroService;

	@RequestMapping(value = "/sayHello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse signUpContact() {
		ResultResponse name = new ResultResponse();
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
	public @ResponseBody ResultResponse profileUpdatation(@RequestBody SignUpUser signUpUser) {
		ResultResponse resultResponse = dariAstroService.profileUpdatation(signUpUser);
		return resultResponse;
	}

	@RequestMapping(value = "/addBirthChart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartResultResponse addBirthChart(@RequestBody BirthChartDetails birthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		birthChartResultResponse = dariAstroService.addBirthChart(birthChartDetails);
		return birthChartResultResponse;
	}

	@RequestMapping(value = "/addMultipleBirthChart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartResultResponse addMultipleBirthChart(
			@RequestBody AddMultipleBirthChartDetails addMultipleBirthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = dariAstroService
				.addMultipleBirthChart(addMultipleBirthChartDetails);
		return birthChartResultResponse;
	}

	@RequestMapping(value = "/editBirthChart", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartResultResponse editBirthChart(@RequestBody BirthChartDetails birthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		birthChartResultResponse = dariAstroService.editBirthChart(birthChartDetails);
		return birthChartResultResponse;
	}

	@RequestMapping(value = "/editMultipleBirthChart", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartResultResponse editMultipleBirthChart(
			@RequestBody AddMultipleBirthChartDetails addMultipleBirthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		birthChartResultResponse = dariAstroService.editMultipleBirthChart(addMultipleBirthChartDetails);
		return birthChartResultResponse;
	}

	@RequestMapping(value = "/deleteBirthChart/{ownerNumber}/{ownerEmail}/{birthChartid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteBirthChart(@PathVariable String ownerNumber,
			@PathVariable String ownerEmail, @PathVariable int birthChartid) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = dariAstroService.deleteBirthChart(ownerNumber, ownerEmail, birthChartid);
		return resultResponse;
	}

	@RequestMapping(value = "/deleteMultipleBirthChart", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteMultipleBirthChart(
			@RequestBody DeleteMultipleBirthCharts deleteMultipleBirthCharts) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = dariAstroService.deleteMultipleBirthChart(deleteMultipleBirthCharts);
		return resultResponse;
	}

	@RequestMapping(value = "/getBirtchartList/{ownerNumber}/{ownerEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BirthChartDetails> getBirtchartList(@PathVariable String ownerNumber,
			@PathVariable String ownerEmail) throws Exception {
		List<BirthChartDetails> birthChartDetailsList = dariAstroService.getBirtchartList(ownerNumber, ownerEmail);
		return birthChartDetailsList;

	}

	@RequestMapping(value = "/getBirtchartById/{ownerNumber}/{ownerEmail}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BirthChartDetails getBirtchartById(@PathVariable String ownerNumber,
			@PathVariable String ownerEmail, @PathVariable int id) throws Exception {
		BirthChartDetails birthChartDetails = dariAstroService.getBirtchartById(ownerNumber, ownerEmail, id);
		return birthChartDetails;

	}

	@RequestMapping(value = "/Trans/KPNatalHoroscope/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpNatalHoroscopeTrans(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpNatalHoroscopeTrans(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	@RequestMapping(value = "/Trans/KPHoraryHoroscope/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpHoraryHoroscope(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpHoraryHoroscope(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	@RequestMapping(value = "/Trans/KPNatalHoroscopePredictions/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpNatalHoroscopePredictions(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpNatalHoroscopePredictions(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	@RequestMapping(value = "/Trans/KPHoraryHoroscopePredictions/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpHoraryHoroscopePredictions(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpHoraryHoroscopePredictions(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	@RequestMapping(value = "/Trans/KPEphemeris/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpEphemeris(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpEphemeris(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	@RequestMapping(value = "/Trans/KPMuhurta/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpMuhurta(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpMuhurta(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	@RequestMapping(value = "/Trans/KPHoroscopeMatching/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse kpHoroscopeMatching(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.kpHoroscopeMatching(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	
	@RequestMapping(value = "/Trans/VedicHoroscope/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse vedicHoroscope(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.vedicHoroscope(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	@RequestMapping(value = "/Trans/VedicHoroscopePredictions/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse vedicHoroscopePredictions(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.vedicHoroscopePredictions(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	@RequestMapping(value = "/Trans/VedicMuhurta/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse vedicMuhurta(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.vedicMuhurta(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	@RequestMapping(value = "/Trans/VedicPanchanga/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse vedicPanchanga(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.vedicPanchanga(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	@RequestMapping(value = "/Trans/HoroscopeMatching/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse horoscopeMatching(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.horoscopeMatching(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	@RequestMapping(value = "/Trans/MundaneAstrology/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse mundaneAstrology(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.mundaneAstrology(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
	
	@RequestMapping(value = "/Trans/Varshphal/{chartName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TransactionResponse varshphal(@PathVariable String chartName,
			@RequestBody TransactionBean transactionBean) {
		TransactionResponse  transactionResponse=new TransactionResponse();
		if(chartName.equalsIgnoreCase(transactionBean.getChartName())) {
			transactionResponse = dariAstroService
				.varshphal(chartName,transactionBean);
			return transactionResponse;
		}
		transactionResponse.setResult("INVALID CHART NAME");
		transactionResponse.setStatus("FALSE");
		return transactionResponse;
	
	}
	
}
