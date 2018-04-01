package com.dari.astro.queries;

public class DariAstroQueries {
	
	public static final String SIGNUP_QUERY_BY_EMAILID = "FROM SignUpUser WHERE emailId = ?";

	public static final String SIGNUP_QUERY_BY_PHONE_NUMBER = "FROM SignUpUser WHERE PHONENUMBER = ?";
	
	public static final String SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER = "from SignUpUser WHERE emailId = ? OR phonenumber = ? ";
	
	public static final String LOGIN_QUERY_BY_EMAILID_OR_PHONENUMBER = "from LoginUserDetails WHERE userEmailId = ? OR userPhoneNumber = ? ";

}
