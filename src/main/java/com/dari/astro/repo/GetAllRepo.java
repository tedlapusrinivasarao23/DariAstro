package com.dari.astro.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

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

@Repository("getAllRepo")
public class GetAllRepo {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public List<KPNatalHoroscope> getAllKPNatalHoroscope(){
		
			List<KPNatalHoroscope> kpNatalHoroscopeList = (List<KPNatalHoroscope>) hibernateTemplate.loadAll(KPNatalHoroscope.class);
			hibernateTemplate.flush();
			hibernateTemplate.clear();
			return kpNatalHoroscopeList;
			
		}

	public List<KPHoraryHoroscope> getAllKPHoraryHoroscope() {
		List<KPHoraryHoroscope> kpHoraryHoroscopeList = (List<KPHoraryHoroscope>) hibernateTemplate.loadAll(KPHoraryHoroscope.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return kpHoraryHoroscopeList;
	}

	public List<KPNatalHoroscopePredictions> getAllKPNatalHoroscopePredictions() {
		List<KPNatalHoroscopePredictions> kpNatalHoroscopePredictionsList = (List<KPNatalHoroscopePredictions>) hibernateTemplate.loadAll(KPNatalHoroscopePredictions.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return kpNatalHoroscopePredictionsList;
	}

	public List<KPHoraryHoroscopePredictions> getAllKPHoraryHoroscopePredictions() {
		List<KPHoraryHoroscopePredictions> kpHoraryHoroscopePredictionsList = (List<KPHoraryHoroscopePredictions>) hibernateTemplate.loadAll(KPHoraryHoroscopePredictions.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return kpHoraryHoroscopePredictionsList;
	}

	public List<KPEphemeris> getAllKPEphemeris() {
		List<KPEphemeris> kpEphemerisList = (List<KPEphemeris>) hibernateTemplate.loadAll(KPEphemeris.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return kpEphemerisList;
	}

	public List<KPMuhurta> getAllKPMuhurta() {
		List<KPMuhurta> kpMuhurtaList = (List<KPMuhurta>) hibernateTemplate.loadAll(KPMuhurta.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return kpMuhurtaList;
	}

	public List<KPHoroscopeMatching> getAllKPHoroscopeMatching() {
		List<KPHoroscopeMatching> kpHoroscopeMatchingList = (List<KPHoroscopeMatching>) hibernateTemplate.loadAll(KPHoroscopeMatching.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return kpHoroscopeMatchingList;
	}

	public List<VedicHoroscope> getAllVedicHoroscope() {
		List<VedicHoroscope> vedicHoroscopeList = (List<VedicHoroscope>) hibernateTemplate.loadAll(VedicHoroscope.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return vedicHoroscopeList;
	}

	public List<VedicHoroscopePredictions> getAllVedicHoroscopePredictions() {
		List<VedicHoroscopePredictions> vedicHoroscopePredictionsList = (List<VedicHoroscopePredictions>) hibernateTemplate.loadAll(VedicHoroscopePredictions.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return vedicHoroscopePredictionsList;
	}

	public List<VedicMuhurta> getAllVedicMuhurta() {
		List<VedicMuhurta> vedicMuhurtaList = (List<VedicMuhurta>) hibernateTemplate.loadAll(VedicMuhurta.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return vedicMuhurtaList;
	}

	public List<VedicPanchanga> getAllVedicPanchanga() {
		List<VedicPanchanga> vedicPanchangaList = (List<VedicPanchanga>) hibernateTemplate.loadAll(VedicPanchanga.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return vedicPanchangaList;
	}

	public List<HoroscopeMatching> getAllHoroscopeMatching() {
		List<HoroscopeMatching> horoscopeMatchingList = (List<HoroscopeMatching>) hibernateTemplate.loadAll(HoroscopeMatching.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return horoscopeMatchingList;
	}

	public List<MundaneAstrology> getAllMundaneAstrology() {
		List<MundaneAstrology> mundaneAstrologyList = (List<MundaneAstrology>) hibernateTemplate.loadAll(MundaneAstrology.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return mundaneAstrologyList;
	}

	public List<Varshphal> getAllVarshphal() {
		List<Varshphal> varshphalList = (List<Varshphal>) hibernateTemplate.loadAll(Varshphal.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return varshphalList;
	}
}
