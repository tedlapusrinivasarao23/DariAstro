package com.dari.astro.utils;

import java.util.List;

public class DeleteMultipleBirthCharts {
	private String ownerNumber;
	private String ownerEmail;
	private List<Integer> birthChartIds;
	
	public String getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public List<Integer> getBirthChartIds() {
		return birthChartIds;
	}
	public void setBirthChartIds(List<Integer> birthChartIds) {
		this.birthChartIds = birthChartIds;
	}
	

}
