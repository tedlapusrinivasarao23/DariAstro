package com.dari.astro.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dari.astro.bos.HoroscopeMatching;
import com.dari.astro.bos.KPEphemeris;
import com.dari.astro.bos.KPHoraryHoroscope;
import com.dari.astro.bos.KPHoraryHoroscopePredictions;
import com.dari.astro.bos.KPHoroscopeMatching;
import com.dari.astro.bos.KPMuhurta;
import com.dari.astro.bos.KPNatalHoroscope;
import com.dari.astro.bos.KPNatalHoroscopePredictions;
import com.dari.astro.bos.MundaneAstrology;
import com.dari.astro.bos.Varshphal;
import com.dari.astro.bos.VedicHoroscope;
import com.dari.astro.bos.VedicHoroscopePredictions;
import com.dari.astro.bos.VedicMuhurta;
import com.dari.astro.bos.VedicPanchanga;
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

	public KPHoraryHoroscope getMappedkpHoraryHoroscope(
			TransactionBean transactionBean) {
		KPHoraryHoroscope kpHoraryHoroscopeMapper = new KPHoraryHoroscope();
		kpHoraryHoroscopeMapper.setChartName(transactionBean.getChartName());
		kpHoraryHoroscopeMapper.setOwnerEmail(transactionBean.getOwnerEmail());
		kpHoraryHoroscopeMapper.setOwnerNumber(transactionBean.getOwnerNumber());
		kpHoraryHoroscopeMapper.setTransaction_Id(transactionBean.getTransaction_Id());
		kpHoraryHoroscopeMapper.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpHoraryHoroscopeMapper.setTransaction_Status(transactionBean.getTransaction_Status());
		kpHoraryHoroscopeMapper.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpHoraryHoroscopeMapper.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpHoraryHoroscopeMapper.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpHoraryHoroscopeMapper.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpHoraryHoroscopeMapper.setChartStatus(true);
		
		kpHoraryHoroscopeMapper.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpHoraryHoroscopeMapper.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpHoraryHoroscopeMapper.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpHoraryHoroscopeMapper.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpHoraryHoroscopeMapper;
	}

	public KPNatalHoroscopePredictions getMappedkpNatalHoroscopePredictions(TransactionBean transactionBean) {
		KPNatalHoroscopePredictions kpNatalHoroscopePredictions = new KPNatalHoroscopePredictions();
		kpNatalHoroscopePredictions.setChartName(transactionBean.getChartName());
		kpNatalHoroscopePredictions.setOwnerEmail(transactionBean.getOwnerEmail());
		kpNatalHoroscopePredictions.setOwnerNumber(transactionBean.getOwnerNumber());
		kpNatalHoroscopePredictions.setTransaction_Id(transactionBean.getTransaction_Id());
		kpNatalHoroscopePredictions.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpNatalHoroscopePredictions.setTransaction_Status(transactionBean.getTransaction_Status());
		kpNatalHoroscopePredictions.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpNatalHoroscopePredictions.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpNatalHoroscopePredictions.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpNatalHoroscopePredictions.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpNatalHoroscopePredictions.setChartStatus(true);
		
		kpNatalHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpNatalHoroscopePredictions.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpNatalHoroscopePredictions.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpNatalHoroscopePredictions.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpNatalHoroscopePredictions;
	}

	public KPHoraryHoroscopePredictions getMappedkpHoraryHoroscopePredictions(TransactionBean transactionBean) {
		
		KPHoraryHoroscopePredictions kpHoraryHoroscopePredictions = new KPHoraryHoroscopePredictions();
		kpHoraryHoroscopePredictions.setChartName(transactionBean.getChartName());
		kpHoraryHoroscopePredictions.setOwnerEmail(transactionBean.getOwnerEmail());
		kpHoraryHoroscopePredictions.setOwnerNumber(transactionBean.getOwnerNumber());
		kpHoraryHoroscopePredictions.setTransaction_Id(transactionBean.getTransaction_Id());
		kpHoraryHoroscopePredictions.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpHoraryHoroscopePredictions.setTransaction_Status(transactionBean.getTransaction_Status());
		kpHoraryHoroscopePredictions.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpHoraryHoroscopePredictions.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpHoraryHoroscopePredictions.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpHoraryHoroscopePredictions.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpHoraryHoroscopePredictions.setChartStatus(true);
		
		kpHoraryHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpHoraryHoroscopePredictions.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpHoraryHoroscopePredictions.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpHoraryHoroscopePredictions.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpHoraryHoroscopePredictions;
	}

	public KPEphemeris getMappedkpEphemeris(TransactionBean transactionBean) {
		
		KPEphemeris kpEphemeris = new KPEphemeris();
		kpEphemeris.setChartName(transactionBean.getChartName());
		kpEphemeris.setOwnerEmail(transactionBean.getOwnerEmail());
		kpEphemeris.setOwnerNumber(transactionBean.getOwnerNumber());
		kpEphemeris.setTransaction_Id(transactionBean.getTransaction_Id());
		kpEphemeris.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpEphemeris.setTransaction_Status(transactionBean.getTransaction_Status());
		kpEphemeris.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpEphemeris.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpEphemeris.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpEphemeris.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpEphemeris.setChartStatus(true);
		
		kpEphemeris.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpEphemeris.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpEphemeris.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpEphemeris.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpEphemeris;
	}

	public KPMuhurta getMappedkpMuhurta(TransactionBean transactionBean) {
		
		KPMuhurta kpMuhurta = new KPMuhurta();
		kpMuhurta.setChartName(transactionBean.getChartName());
		kpMuhurta.setOwnerEmail(transactionBean.getOwnerEmail());
		kpMuhurta.setOwnerNumber(transactionBean.getOwnerNumber());
		kpMuhurta.setTransaction_Id(transactionBean.getTransaction_Id());
		kpMuhurta.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpMuhurta.setTransaction_Status(transactionBean.getTransaction_Status());
		kpMuhurta.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpMuhurta.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpMuhurta.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpMuhurta.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpMuhurta.setChartStatus(true);
		
		kpMuhurta.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpMuhurta.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpMuhurta.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpMuhurta.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpMuhurta;
	}

	public KPHoroscopeMatching getMappedkpHoroscopeMatching(TransactionBean transactionBean) {
		
		KPHoroscopeMatching kpHoroscopeMatching = new KPHoroscopeMatching();
		kpHoroscopeMatching.setChartName(transactionBean.getChartName());
		kpHoroscopeMatching.setOwnerEmail(transactionBean.getOwnerEmail());
		kpHoroscopeMatching.setOwnerNumber(transactionBean.getOwnerNumber());
		kpHoroscopeMatching.setTransaction_Id(transactionBean.getTransaction_Id());
		kpHoroscopeMatching.setTransaction_Amount(transactionBean.getTransaction_Amount());
		kpHoroscopeMatching.setTransaction_Status(transactionBean.getTransaction_Status());
		kpHoroscopeMatching.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		kpHoroscopeMatching.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		kpHoroscopeMatching.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		kpHoroscopeMatching.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		kpHoroscopeMatching.setChartStatus(true);
		
		kpHoroscopeMatching.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpHoroscopeMatching.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		kpHoroscopeMatching.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		kpHoroscopeMatching.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return kpHoroscopeMatching;
	}

	public VedicHoroscope getMappedvedicHoroscope(TransactionBean transactionBean) {
		
		VedicHoroscope vedicHoroscope = new VedicHoroscope();
		vedicHoroscope.setChartName(transactionBean.getChartName());
		vedicHoroscope.setOwnerEmail(transactionBean.getOwnerEmail());
		vedicHoroscope.setOwnerNumber(transactionBean.getOwnerNumber());
		vedicHoroscope.setTransaction_Id(transactionBean.getTransaction_Id());
		vedicHoroscope.setTransaction_Amount(transactionBean.getTransaction_Amount());
		vedicHoroscope.setTransaction_Status(transactionBean.getTransaction_Status());
		vedicHoroscope.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
		vedicHoroscope.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
		vedicHoroscope.setTransaction_Device_ID(transactionBean.getTransaction_Id());
		vedicHoroscope.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
		vedicHoroscope.setChartStatus(true);
		
		vedicHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		vedicHoroscope.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
		vedicHoroscope.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
		vedicHoroscope.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
		
		return vedicHoroscope;
	}

	public VedicHoroscopePredictions getMappedvedicHoroscopePredictions(TransactionBean transactionBean) {
		
	VedicHoroscopePredictions vedicHoroscopePredictions = new VedicHoroscopePredictions();
	
	vedicHoroscopePredictions.setChartName(transactionBean.getChartName());
	vedicHoroscopePredictions.setOwnerEmail(transactionBean.getOwnerEmail());
	vedicHoroscopePredictions.setOwnerNumber(transactionBean.getOwnerNumber());
	vedicHoroscopePredictions.setTransaction_Id(transactionBean.getTransaction_Id());
	vedicHoroscopePredictions.setTransaction_Amount(transactionBean.getTransaction_Amount());
	vedicHoroscopePredictions.setTransaction_Status(transactionBean.getTransaction_Status());
	vedicHoroscopePredictions.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
	vedicHoroscopePredictions.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
	vedicHoroscopePredictions.setTransaction_Device_ID(transactionBean.getTransaction_Id());
	vedicHoroscopePredictions.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
	vedicHoroscopePredictions.setChartStatus(true);
	
	vedicHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	vedicHoroscopePredictions.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	vedicHoroscopePredictions.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
	vedicHoroscopePredictions.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
	
	return vedicHoroscopePredictions;
	}

	public VedicMuhurta getMappedvedicMuhurta(TransactionBean transactionBean) {
		
		VedicMuhurta vedicMuhurta = new VedicMuhurta();
	
	vedicMuhurta.setChartName(transactionBean.getChartName());
	vedicMuhurta.setOwnerEmail(transactionBean.getOwnerEmail());
	vedicMuhurta.setOwnerNumber(transactionBean.getOwnerNumber());
	vedicMuhurta.setTransaction_Id(transactionBean.getTransaction_Id());
	vedicMuhurta.setTransaction_Amount(transactionBean.getTransaction_Amount());
	vedicMuhurta.setTransaction_Status(transactionBean.getTransaction_Status());
	vedicMuhurta.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
	vedicMuhurta.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
	vedicMuhurta.setTransaction_Device_ID(transactionBean.getTransaction_Id());
	vedicMuhurta.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
	vedicMuhurta.setChartStatus(true);
	
	vedicMuhurta.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	vedicMuhurta.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	vedicMuhurta.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
	vedicMuhurta.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
	
	return vedicMuhurta;
	}

	public VedicPanchanga getMappedvedicPanchanga(TransactionBean transactionBean) {
		
		VedicPanchanga vedicPanchanga = new VedicPanchanga();
	
	vedicPanchanga.setChartName(transactionBean.getChartName());
	vedicPanchanga.setOwnerEmail(transactionBean.getOwnerEmail());
	vedicPanchanga.setOwnerNumber(transactionBean.getOwnerNumber());
	vedicPanchanga.setTransaction_Id(transactionBean.getTransaction_Id());
	vedicPanchanga.setTransaction_Amount(transactionBean.getTransaction_Amount());
	vedicPanchanga.setTransaction_Status(transactionBean.getTransaction_Status());
	vedicPanchanga.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
	vedicPanchanga.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
	vedicPanchanga.setTransaction_Device_ID(transactionBean.getTransaction_Id());
	vedicPanchanga.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
	vedicPanchanga.setChartStatus(true);
	
	vedicPanchanga.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	vedicPanchanga.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	vedicPanchanga.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
	vedicPanchanga.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
	
	return vedicPanchanga;
	}

	public HoroscopeMatching getMappedhoroscopeMatching(TransactionBean transactionBean) {
		
		HoroscopeMatching horoscopeMatching = new HoroscopeMatching();
	
	horoscopeMatching.setChartName(transactionBean.getChartName());
	horoscopeMatching.setOwnerEmail(transactionBean.getOwnerEmail());
	horoscopeMatching.setOwnerNumber(transactionBean.getOwnerNumber());
	horoscopeMatching.setTransaction_Id(transactionBean.getTransaction_Id());
	horoscopeMatching.setTransaction_Amount(transactionBean.getTransaction_Amount());
	horoscopeMatching.setTransaction_Status(transactionBean.getTransaction_Status());
	horoscopeMatching.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
	horoscopeMatching.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
	horoscopeMatching.setTransaction_Device_ID(transactionBean.getTransaction_Id());
	horoscopeMatching.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
	horoscopeMatching.setChartStatus(true);
	
	horoscopeMatching.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	horoscopeMatching.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	horoscopeMatching.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
	horoscopeMatching.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
	
	return horoscopeMatching;
	}

	public MundaneAstrology getMappedmundaneAstrology(TransactionBean transactionBean) {
		
		MundaneAstrology mundaneAstrology = new MundaneAstrology();
	
	mundaneAstrology.setChartName(transactionBean.getChartName());
	mundaneAstrology.setOwnerEmail(transactionBean.getOwnerEmail());
	mundaneAstrology.setOwnerNumber(transactionBean.getOwnerNumber());
	mundaneAstrology.setTransaction_Id(transactionBean.getTransaction_Id());
	mundaneAstrology.setTransaction_Amount(transactionBean.getTransaction_Amount());
	mundaneAstrology.setTransaction_Status(transactionBean.getTransaction_Status());
	mundaneAstrology.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
	mundaneAstrology.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
	mundaneAstrology.setTransaction_Device_ID(transactionBean.getTransaction_Id());
	mundaneAstrology.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
	mundaneAstrology.setChartStatus(true);
	
	mundaneAstrology.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	mundaneAstrology.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	mundaneAstrology.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
	mundaneAstrology.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
	
	return mundaneAstrology;
	}

	public Varshphal getMappedvarshphal(TransactionBean transactionBean) {
		
		Varshphal varshphal = new Varshphal();
	
	varshphal.setChartName(transactionBean.getChartName());
	varshphal.setOwnerEmail(transactionBean.getOwnerEmail());
	varshphal.setOwnerNumber(transactionBean.getOwnerNumber());
	varshphal.setTransaction_Id(transactionBean.getTransaction_Id());
	varshphal.setTransaction_Amount(transactionBean.getTransaction_Amount());
	varshphal.setTransaction_Status(transactionBean.getTransaction_Status());
	varshphal.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
	varshphal.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
	varshphal.setTransaction_Device_ID(transactionBean.getTransaction_Id());
	varshphal.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
	varshphal.setChartStatus(true);
	
	varshphal.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	varshphal.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
	varshphal.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
	varshphal.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
	
	return varshphal;
	}

}
