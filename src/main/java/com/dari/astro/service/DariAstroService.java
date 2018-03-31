package com.dari.astro.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dari.astro.bos.SignUpUser;
import com.dari.astro.mappers.DariTimeMappers;
import com.dari.astro.mappers.SendEmail;
import com.dari.astro.mappers.SendSms;
import com.dari.astro.repo.DariAstroCRUD;
import com.dari.astro.repo.DariAstroRepo;
import com.dari.astro.utils.ResultResponse;

@Service("dariAstroService")
public class DariAstroService {
	
	@Autowired
	private DariAstroRepo dariAstroRepo;
	
	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private SendSms sendSms;
	
	@Autowired
	private DariAstroCRUD dariAstroCRUD;
	
	@Autowired
	private DariTimeMappers dariTimeMappers;
	
	@Transactional
	public ResultResponse signUpUser(SignUpUser signUpUser) {

		ResultResponse resultResponse = new ResultResponse();
		String uuid = UUID.randomUUID().toString();
		String[] newPasswords = uuid.split("-");
		String newpassword = newPasswords[0];

		try {
			SignUpUser SignUpUserList = dariAstroRepo.getSignUpContactByEmailId(signUpUser);
			if (SignUpUserList.getEmailId().equals(signUpUser.getEmailId())) {
				resultResponse.setResult("Given Email_Id Already Exists");
				resultResponse.setStatus("false");
				return resultResponse;
			}
		} catch (Exception e) {

			try {
				SignUpUser SignUpUserList = dariAstroRepo.getSignUpContactByPhoneNumber(signUpUser);
				if (SignUpUserList.getPhoneNumber().equals(signUpUser.getPhoneNumber())) {
					resultResponse.setResult("Given Phone Number Already Exists");
					resultResponse.setStatus("false");
					return resultResponse;

				}
			} catch (Exception exe) {
				signUpUser.setPassword(newpassword);
				signUpUser.setSignupDateAndTime(dariTimeMappers.getCurrentSystemDateAndTime());
				signUpUser.setIsActive("NO");
				dariAstroCRUD.methodForSave(signUpUser);
			}
		}
		sendEmail.sendEmailForgotPassword(signUpUser.getEmailId(), newpassword,
				" Welcome To dariastro.com Please use the below credentials to login for the first time and reset the password after login. Username: "
						+ signUpUser.getEmailId() + " Password: ");
		sendSms.sendSMS(signUpUser.getPhoneNumber(), newpassword,
				" Welcome To dariastro.com Please use the below credentials to login for the first time and reset the password after login. Username: "
						+ signUpUser.getEmailId() + " Password: ");
		resultResponse.setResult("Sign Up done Successfully password Sent to your registered Email and Phone Number");
		resultResponse.setStatus("true");
		return resultResponse;
	}

	
	

}
