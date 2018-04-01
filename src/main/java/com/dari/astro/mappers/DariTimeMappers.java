package com.dari.astro.mappers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("dariTimeMappers")
public class DariTimeMappers {
	
	public String getCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;

	}

	public String getOneYearLaterCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = cal.getTime();

		String reportDate = df.format(nextYear);
		return reportDate;

	}

	public long daysBetween() {

		Date today1 = Calendar.getInstance().getTime();

		Calendar ca2 = Calendar.getInstance();
		Date today = ca2.getTime();
		ca2.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = ca2.getTime();

		long difference = (nextYear.getTime() - today1.getTime()) / 86400000;
		return Math.abs(difference);
	}

}
