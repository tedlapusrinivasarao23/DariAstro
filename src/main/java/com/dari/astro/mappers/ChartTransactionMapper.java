package com.dari.astro.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dari.astro.bos.KPNatalHoroscope;
import com.dari.astro.utils.TransactionBean;

@Component("chartTransactionMapper")
public class ChartTransactionMapper {
	
	@Autowired
	private DariTimeMappers dariTimeMappers;
	
	public KPNatalHoroscope getMappedKPNatalHoroscope(KPNatalHoroscope kpNatalHoroscope,TransactionBean transactionBean ) {
		KPNatalHoroscope kpNatalHoroscopeMapper = new KPNatalHoroscope();
		kpNatalHoroscopeMapper.setChartName(transactionBean.getChartName());
		kpNatalHoroscopeMapper.setOwnerEmail(transactionBean.getOwnerEmail());
		kpNatalHoroscopeMapper.setOwnerNumber(transactionBean.getOwnerNumber());
		kpNatalHoroscopeMapper.setTransaction_Id(transactionBean.getTransaction_Id());
		kpNatalHoroscopeMapper.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpNatalHoroscopeMapper.setTransaction_Status(transactionBean.getTransaction_Status());
		kpNatalHoroscopeMapper.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpNatalHoroscopeMapper.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpNatalHoroscopeMapper.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpNatalHoroscopeMapper.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpNatalHoroscopeMapper.setChartStatus(true);
		
		kpNatalHoroscopeMapper.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpNatalHoroscopeMapper.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpNatalHoroscopeMapper.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpNatalHoroscopeMapper.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpNatalHoroscopeMapper;
	}

}
