package com.dari.astro.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.dari.astro.bos.BirthChartDetails;
import com.dari.astro.bos.HoroscopeMatching;
import com.dari.astro.bos.KPEphemeris;
import com.dari.astro.bos.KPHoraryHoroscope;
import com.dari.astro.bos.KPHoraryHoroscopePredictions;
import com.dari.astro.bos.KPHoroscopeMatching;
import com.dari.astro.bos.KPMuhurta;
import com.dari.astro.bos.KPNatalHoroscope;
import com.dari.astro.bos.KPNatalHoroscopePredictions;
import com.dari.astro.bos.LoginUserDetails;
import com.dari.astro.bos.MundaneAstrology;
import com.dari.astro.bos.SignUpUser;
import com.dari.astro.bos.Varshphal;
import com.dari.astro.bos.VedicHoroscope;
import com.dari.astro.bos.VedicHoroscopePredictions;
import com.dari.astro.bos.VedicMuhurta;
import com.dari.astro.bos.VedicPanchanga;
import com.dari.astro.queries.DariAstroQueries;
import com.dari.astro.utils.ForgotPassword;
import com.dari.astro.utils.LoginUser;
import com.dari.astro.utils.LogoutUser;
import com.dari.astro.utils.UpdatePassword;

@Repository("dariAstroRepo")
public class DariAstroRepo {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public SignUpUser getSignUpContactByEmailId(SignUpUser signUpUser) {

		SignUpUser SignUpUsertList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID, new Object[] { signUpUser.getEmailId() }).get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();

		return SignUpUsertList;
	}

	public SignUpUser getSignUpContactByPhoneNumber(SignUpUser signUpUser) {

		SignUpUser signUpContactList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_PHONE_NUMBER, new Object[] { signUpUser.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	public SignUpUser getSignUpContactByEmailIdAndPhoneNumber(LoginUser loginUser) {

		SignUpUser signUpUserList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { loginUser.getEmailId(), loginUser.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpUserList;
	}

	public List<LoginUserDetails> getCheckForFirstLogin(String phoneNumber, String emailId) {

		@SuppressWarnings("unchecked")
		List<LoginUserDetails> loginUserDetailsList = (List<LoginUserDetails>) hibernateTemplate
				.find(DariAstroQueries.LOGIN_QUERY_BY_EMAILID_OR_PHONENUMBER, new Object[] { emailId, phoneNumber });

		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return loginUserDetailsList;
	}

	public SignUpUser getSignUpContactByEmailIdAndPhoneNumber(LogoutUser logoutUser) {

		SignUpUser signUpUserList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { logoutUser.getUserEmailId(), logoutUser.getUserPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpUserList;
	}

	public SignUpUser getSignUpContactByEmailIdAndPhoneNumberForUpdatePassword(UpdatePassword updatePassword) {

		SignUpUser signUpUsertList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { updatePassword.getEmailId(), updatePassword.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpUsertList;
	}
	
	public SignUpUser getSignUpContactByEmailIdAndPhoneNumber(ForgotPassword forgotPassword) {

		SignUpUser signUpUserList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { forgotPassword.getOwnerEmailId(), forgotPassword.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpUserList;
	}
	
	public BirthChartDetails getBirthChartByNumberAndEmailAndId(String ownerNumber,String OwnerEmail,int id) {
		BirthChartDetails birthChartDetails = (BirthChartDetails) hibernateTemplate
				.find(DariAstroQueries.GET_BIRTHCHART_BY_OWNERNUMBER_AND_OWNEREMAIL_AND_ID, new Object[] {ownerNumber,OwnerEmail,id}).get(0);
		return birthChartDetails;
	}

	public List<BirthChartDetails> getBirthChartByOwnerNumberOrOwnerEmail(String ownerNumber, String ownerEmail) {
		List<BirthChartDetails> birthChartDetailsList = (List<BirthChartDetails>) hibernateTemplate
				.find(DariAstroQueries.GET_BIRTHCHART_BY_OWNERNUMBER_OR_OWNEREMAIL, new Object[] { ownerNumber, ownerEmail });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return birthChartDetailsList;
	}
	
	public SignUpUser getKPEmailIdAndPhoneNumberForUpdatePassword(UpdatePassword updatePassword) {

		SignUpUser signUpUsertList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { updatePassword.getEmailId(), updatePassword.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpUsertList;
	}

	
	public KPNatalHoroscope getKPNatalHoroscopeByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {

		KPNatalHoroscope kpNatalHoroscope = (KPNatalHoroscope) hibernateTemplate
				.find(DariAstroQueries.KP_NATAL_HOROSCOPE_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpNatalHoroscope;
	}
	
	public KPHoraryHoroscope getkpHoraryHoroscopeByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {

		KPHoraryHoroscope kpHoraryHoroscope = (KPHoraryHoroscope) hibernateTemplate
				.find(DariAstroQueries.KP_HORARY_HOROSCOPE_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpHoraryHoroscope;
	}

	public KPNatalHoroscopePredictions getkpNatalHoroscopePredictionsByEmailIdAndPhoneNumber(String ownerEmail,
			String ownerNumber) {
		KPNatalHoroscopePredictions kpNatalHoroscopePredictions = (KPNatalHoroscopePredictions) hibernateTemplate
				.find(DariAstroQueries.KP_NATAL_HOROSCOPE_PREDICTIONS_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpNatalHoroscopePredictions;
	}

	public KPHoraryHoroscopePredictions getkpHoraryHoroscopePredictionsByEmailIdAndPhoneNumber(String ownerEmail,
			String ownerNumber) {
		KPHoraryHoroscopePredictions kpHoraryHoroscopePredictions = (KPHoraryHoroscopePredictions) hibernateTemplate
				.find(DariAstroQueries.KP_HORARY_HOROSCOPE_PREDICTIONS_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpHoraryHoroscopePredictions;
	}

	public KPEphemeris getkpEphemerisByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		KPEphemeris kpEphemeris = (KPEphemeris) hibernateTemplate
				.find(DariAstroQueries.KP_EPHEMERIS_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpEphemeris;
	}

	public KPMuhurta getkpMuhurtaByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		KPMuhurta kpMuhurta = (KPMuhurta) hibernateTemplate
				.find(DariAstroQueries.KP_MUHURTA_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpMuhurta;
	}

	public KPHoroscopeMatching getkpHoroscopeMatchingByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		KPHoroscopeMatching kpHoroscopeMatching = (KPHoroscopeMatching) hibernateTemplate
				.find(DariAstroQueries.KP_HOROSCOPE_MATCHING_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return kpHoroscopeMatching;
	}

	
	public VedicHoroscope getvedicHoroscopeByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		VedicHoroscope vedicHoroscope = (VedicHoroscope) hibernateTemplate
				.find(DariAstroQueries.VEDIC_HOROSCOPE_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return vedicHoroscope;
	}

	public VedicHoroscopePredictions getvedicHoroscopePredictionsByEmailIdAndPhoneNumber(String ownerEmail,
			String ownerNumber) {
		VedicHoroscopePredictions vedicHoroscopePredictions = (VedicHoroscopePredictions) hibernateTemplate
				.find(DariAstroQueries.VEDIC_HOROSCOPE_PREDICTIONS_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return vedicHoroscopePredictions;
	}

	public VedicMuhurta getvedicMuhurtaByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		VedicMuhurta vedicMuhurta = (VedicMuhurta) hibernateTemplate
				.find(DariAstroQueries.VEDIC_MUHURTA_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return vedicMuhurta;
	}

	public VedicPanchanga getvedicPanchangaByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		VedicPanchanga vedicPanchanga = (VedicPanchanga) hibernateTemplate
				.find(DariAstroQueries.VEDIC_PANCHANGA_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return vedicPanchanga;
	}

	public HoroscopeMatching gethoroscopeMatchingByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		HoroscopeMatching horoscopeMatching = (HoroscopeMatching) hibernateTemplate
				.find(DariAstroQueries.HOROSCOPE_PANCHANGA_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return horoscopeMatching;
	}

	public MundaneAstrology getmundaneAstrologyByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		MundaneAstrology mundaneAstrology = (MundaneAstrology) hibernateTemplate
				.find(DariAstroQueries.MUNDANE_ASTROLOGY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return mundaneAstrology;
	}

	public Varshphal getvarshphalByEmailIdAndPhoneNumber(String ownerEmail, String ownerNumber) {
		Varshphal varshphal = (Varshphal) hibernateTemplate
				.find(DariAstroQueries.VARSHPHAL_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		
		return varshphal;
	}
	
	
}
