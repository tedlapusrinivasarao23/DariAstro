package com.dari.astro.queries;

public class DariAstroQueries {
	
	public static final String SIGNUP_QUERY_BY_EMAILID = "FROM SignUpUser WHERE emailId = ?";

	public static final String SIGNUP_QUERY_BY_PHONE_NUMBER = "FROM SignUpUser WHERE PHONENUMBER = ?";
	
	public static final String SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER = "from SignUpUser WHERE emailId = ? OR phonenumber = ? ";
	
	public static final String LOGIN_QUERY_BY_EMAILID_OR_PHONENUMBER = "from LoginUserDetails WHERE userEmailId = ? OR userPhoneNumber = ? ";
	
	public static final String GET_BIRTHCHART_BY_OWNERNUMBER_AND_OWNEREMAIL_AND_ID = "from BirthChartDetails WHERE (ownerNumber = ? OR ownerEmail = ?) AND id = ?" ;

	public static final String GET_BIRTHCHART_BY_OWNERNUMBER_OR_OWNEREMAIL = "from BirthChartDetails WHERE ownerNumber = ? OR ownerEmail = ? ";

	public static final String KP_NATAL_HOROSCOPE_BY_EMAILID_OR_PHONENUMBER = "from KPNatalHoroscope WHERE ownerEmail = ? OR ownerNumber = ? ";
	
	public static final String KP_HORARY_HOROSCOPE_BY_EMAILID_OR_PHONENUMBER = "from KPHoraryHoroscope WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String KP_NATAL_HOROSCOPE_PREDICTIONS_BY_EMAILID_OR_PHONENUMBER = "from KPNatalHoroscopePredictions WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String KP_HORARY_HOROSCOPE_PREDICTIONS_BY_EMAILID_OR_PHONENUMBER = "from KPHoraryHoroscopePredictions WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String KP_EPHEMERIS_BY_EMAILID_OR_PHONENUMBER = "from KPEphemeris WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String KP_MUHURTA_BY_EMAILID_OR_PHONENUMBER = "from KPMuhurta WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String KP_HOROSCOPE_MATCHING_BY_EMAILID_OR_PHONENUMBER = "from KPHoroscopeMatching WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String VEDIC_HOROSCOPE_BY_EMAILID_OR_PHONENUMBER = "from VedicHoroscope WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String VEDIC_HOROSCOPE_PREDICTIONS_BY_EMAILID_OR_PHONENUMBER = "from VedicHoroscopePredictions WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String VEDIC_MUHURTA_BY_EMAILID_OR_PHONENUMBER = "from VedicMuhurta WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String VEDIC_PANCHANGA_BY_EMAILID_OR_PHONENUMBER = "from VedicPanchanga WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String HOROSCOPE_PANCHANGA_BY_EMAILID_OR_PHONENUMBER = "from HoroscopeMatching WHERE ownerEmail = ? OR ownerNumber = ? ";

	public static final String MUNDANE_ASTROLOGY_BY_EMAILID_OR_PHONENUMBER = "from MundaneAstrology WHERE ownerEmail = ? OR ownerNumber = ? ";
	
	public static final String VARSHPHAL_BY_EMAILID_OR_PHONENUMBER = "from Varshphal WHERE ownerEmail = ? OR ownerNumber = ? ";
	
}
