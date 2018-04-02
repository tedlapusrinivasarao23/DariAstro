package com.dari.astro.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dari.astro.bos.LoginUserDetails;
import com.dari.astro.bos.LogoutContactDetails;
import com.dari.astro.bos.SignUpUser;
import com.dari.astro.mappers.DariTimeMappers;
import com.dari.astro.mappers.SendEmail;
import com.dari.astro.mappers.SendSms;
import com.dari.astro.repo.DariAstroCRUD;
import com.dari.astro.repo.DariAstroRepo;
import com.dari.astro.utils.LoginResponse;
import com.dari.astro.utils.LoginUser;
import com.dari.astro.utils.LogoutResponse;
import com.dari.astro.utils.LogoutUser;
import com.dari.astro.utils.ResultResponse;
import com.dari.astro.utils.UpdatePassword;

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

	@Transactional
	public LoginResponse loginUser(LoginUser loginUser) {

		LoginResponse loginResponse = new LoginResponse();

		try {
			SignUpUser signUpUserList = dariAstroRepo.getSignUpContactByEmailIdAndPhoneNumber(loginUser);

			List<LoginUserDetails> loginUserDetailsList = dariAstroRepo
					.getCheckForFirstLogin(loginUser.getPhoneNumber(), loginUser.getEmailId());

			if (signUpUserList.getPassword().equals(loginUser.getPassword())
					&& signUpUserList.getIsActive().equalsIgnoreCase("NO")) {

				LoginUserDetails loginUserDetails = new LoginUserDetails();

				loginUserDetails.setUserEmailId(loginUser.getEmailId());
				loginUserDetails.setUserPhoneNumber(loginUser.getPhoneNumber());
				loginUserDetails.setLastLoginDeviceID(loginUser.getLoginDeviceID());
				loginUserDetails.setLastLoginDeviceOS(loginUser.getLoginDeviceOS());
				loginUserDetails.setLastLoginLocation(loginUser.getLoginLocation());
				loginUserDetails.setLastLoginTime(dariTimeMappers.getCurrentSystemDateAndTime());
				loginUserDetails.setLastLoginDeviceOSVersion(loginUser.getLoginDeviceOSVersion());
				loginUserDetails.setLastLoginDeviceModel(loginUser.getLoginDeviceModel());
				signUpUserList.setIsActive("YES");
				dariAstroCRUD.methodForUpdate(signUpUserList);
				dariAstroCRUD.methodForSave(loginUserDetails);

				if (loginUserDetailsList.size() > 0) {
					loginResponse.setFirstLoginMessage("Not First Login");
				} else {
					loginResponse.setFirstLoginMessage("First Login");
				}

				loginResponse.setUserName(signUpUserList.getUserName());
				loginResponse.setEmailId(signUpUserList.getEmailId());
				loginResponse.setPhoneNumber(signUpUserList.getPhoneNumber());
				loginResponse.setProfileImage(signUpUserList.getImage());
				// loginResponse.setAddress(signUpUserList.getAddress());
				loginResponse.setPaid(signUpUserList.isPaid());
				loginResponse.setLoginMessage("Login Successful");

				return loginResponse;
			} else if (signUpUserList.getPassword().equals(loginUser.getPassword())
					&& signUpUserList.getIsActive().equalsIgnoreCase("YES")) {
				loginResponse.setLoginMessage("Already Logged in with Other Device");
				return loginResponse;
			}

		} catch (Exception e) {
			loginResponse.setLoginMessage("In valid " + loginUser.getEmailId() + "" + loginUser.getPhoneNumber());
			return loginResponse;

		}
		loginResponse.setLoginMessage("Invalid Password");

		return loginResponse;
	}

	@Transactional
	public LogoutResponse logoutContact(LogoutUser logoutUser) {

		LogoutResponse logoutResponse = new LogoutResponse();

		try {
			SignUpUser signUpUserList = dariAstroRepo.getSignUpContactByEmailIdAndPhoneNumber(logoutUser);

			LogoutContactDetails logoutContactDetails = new LogoutContactDetails();
			logoutContactDetails.setLogoutDeviceIDHistory(logoutUser.getLogoutDeviceID());
			logoutContactDetails.setLogoutDeviceOSHistory(logoutUser.getLogoutDeviceOS());
			logoutContactDetails.setLogoutLocationHistory(logoutUser.getLogoutLocation());
			logoutContactDetails.setLogoutTimeAndDateHistory(dariTimeMappers.getCurrentSystemDateAndTime());
			logoutContactDetails.setUserEmailId(logoutUser.getUserEmailId());
			logoutContactDetails.setUserPhoneNumber(logoutUser.getUserPhoneNumber());
			logoutContactDetails.setLogoutDeviceModel(logoutUser.getLogoutDeviceModel());
			logoutContactDetails.setLogoutDeviceOSVersion(logoutUser.getLogoutDeviceOSVersion());
			signUpUserList.setIsActive("NO");
			dariAstroCRUD.methodForUpdate(signUpUserList);
			dariAstroCRUD.methodForSave(logoutContactDetails);
			logoutResponse.setLogoutResult("Successfully LoggedOut");

			return logoutResponse;
		} catch (Exception e) {

		}
		return logoutResponse;

	}

	@Transactional
	public ResultResponse updatePassword(UpdatePassword updatePassword) {

		ResultResponse resultResponse = new ResultResponse();

		try {
			SignUpUser signUpUserList = dariAstroRepo
					.getSignUpContactByEmailIdAndPhoneNumberForUpdatePassword(updatePassword);
			if (signUpUserList.getPassword().equals(updatePassword.getOldPassword())) {
				signUpUserList.setPassword(updatePassword.getNewPassword());
				dariAstroCRUD.methodForUpdate(signUpUserList);
				resultResponse.setResult("Password Updated Successfully");
				resultResponse.setStatus("true");
				return resultResponse;
			}
		} catch (Exception e) {
			resultResponse.setResult(
					"No User Exists with Given :" + updatePassword.getEmailId() + updatePassword.getPhoneNumber());
			resultResponse.setStatus("false");
			return resultResponse;
		}
		resultResponse.setResult("Invalid Old Password");
		resultResponse.setStatus("false");
		return resultResponse;

	}

}
