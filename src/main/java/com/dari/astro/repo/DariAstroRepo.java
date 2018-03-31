package com.dari.astro.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.dari.astro.bos.SignUpUser;
import com.dari.astro.queries.DariAstroQueries;

@Repository("dariAstroRepo")
public class DariAstroRepo {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public SignUpUser getSignUpContactByEmailId(SignUpUser signUpUser) {

		SignUpUser SignUpUsertList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_EMAILID, new Object[] { signUpUser.getEmailId() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();

		return SignUpUsertList;
	}

	public SignUpUser getSignUpContactByPhoneNumber(SignUpUser signUpUser) {

		SignUpUser signUpContactList = (SignUpUser) hibernateTemplate
				.find(DariAstroQueries.SIGNUP_QUERY_BY_PHONE_NUMBER,
						new Object[] { signUpUser.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	
}
