package com.dari.astro.utils;

import java.util.List;

import com.dari.astro.bos.BirthChartDetails;

public class BirthChartResultResponse {
	
	private BirthChartDetails birthChartDetails;
	private List<BirthChartDetails> birthChartDetailsList;
	private String result;
	private String status;
	private List<String> unAddedbirthCharts;
	
	public BirthChartDetails getBirthChartDetails() {
		return birthChartDetails;
	}
	public void setBirthChartDetails(BirthChartDetails birthChartDetails) {
		this.birthChartDetails = birthChartDetails;
	}
	
	
	public List<BirthChartDetails> getBirthChartDetailsList() {
		return birthChartDetailsList;
	}
	public void setBirthChartDetailsList(List<BirthChartDetails> birthChartDetailsList) {
		this.birthChartDetailsList = birthChartDetailsList;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getUnAddedbirthCharts() {
		return unAddedbirthCharts;
	}
	public void setUnAddedbirthCharts(List<String> unAddedbirthCharts) {
		this.unAddedbirthCharts = unAddedbirthCharts;
	}
	
	

}
