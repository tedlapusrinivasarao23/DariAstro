package com.dari.astro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dari.astro.bos.BirthChartDetails;
import com.dari.astro.bos.LoginUserDetails;
import com.dari.astro.bos.LogoutContactDetails;
import com.dari.astro.bos.SignUpUser;
import com.dari.astro.mappers.DariTimeMappers;
import com.dari.astro.mappers.SendEmail;
import com.dari.astro.mappers.SendSms;
import com.dari.astro.repo.DariAstroCRUD;
import com.dari.astro.repo.DariAstroRepo;
import com.dari.astro.utils.AddMultipleBirthChartDetails;
import com.dari.astro.utils.BirthChartResultResponse;
import com.dari.astro.utils.ForgotPassword;
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

			if ((signUpUserList.getPassword().equals(loginUser.getPassword())
					&& signUpUserList.getIsActive().equalsIgnoreCase("NO")) || ((signUpUserList.getPassword().equals(loginUser.getPassword()) && (signUpUserList.getSignupDeviceID().equalsIgnoreCase(loginUser.getLoginDeviceID())) ))) {

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
				loginResponse.setLoginMessage("Already Logged in with Other Device, Please Logout and Come Back");
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
	
	
	@Transactional
	public ResultResponse forgotPassword(ForgotPassword forgotPassword) {

		ResultResponse resultResponse = new ResultResponse();
		boolean result = false;
		boolean resultSms = false;
		String uuid = UUID.randomUUID().toString();
		String[] newPasswords = uuid.split("-");
		String newpassword = newPasswords[0];

		SignUpUser signUpUser = null;

		try {
			signUpUser = dariAstroRepo.getSignUpContactByEmailIdAndPhoneNumber(forgotPassword);

			UpdatePassword updatePasswordEmail = new UpdatePassword();
			updatePasswordEmail.setEmailId(signUpUser.getEmailId());
			updatePasswordEmail.setOldPassword(signUpUser.getPassword());
			updatePasswordEmail.setPhoneNumber(signUpUser.getPhoneNumber());

			result = sendEmail.sendEmailForgotPassword(signUpUser.getEmailId(), newpassword,
					"Here is your new password : ");
			resultSms = sendSms.sendSMS(signUpUser.getPhoneNumber(), newpassword, "Here is your new password : ");

			if (result == true || resultSms == true) {
				updatePasswordEmail.setNewPassword(newpassword);
				this.forgotPasswordDBUpdate(updatePasswordEmail);
				resultResponse.setResult("New Password Sent Successfully to your registered EmailId and Mobile Number");
				resultResponse.setStatus("true");
				return resultResponse;
			} else {
				resultResponse.setResult("Please try again there is problem with network");
				resultResponse.setStatus("false");
				return resultResponse;
			}

		} catch (Exception e) {
			resultResponse.setResult("Provide Valid EmailId or Phone Number");
			resultResponse.setStatus("false");
			return resultResponse;
		}

	}
	
	@Transactional
	public ResultResponse forgotPasswordDBUpdate(UpdatePassword updatePassword) {

		ResultResponse resultResponse = new ResultResponse();

		try {
			SignUpUser signUpUserList = dariAstroRepo
					.getSignUpContactByEmailIdAndPhoneNumberForUpdatePassword(updatePassword);
			if (signUpUserList.getPassword().equals(updatePassword.getOldPassword())) {
				signUpUserList.setPassword(updatePassword.getNewPassword());
				signUpUserList.setIsActive("NO");
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
	
	@Transactional
	public ResultResponse profileUpdatation(SignUpUser signUpUser) {

		ResultResponse resultResponse = new ResultResponse();
		SignUpUser contactSignUp = new SignUpUser();

		SignUpUser contact = dariAstroRepo.getSignUpContactByPhoneNumber(signUpUser);
		
		contact.setImage(signUpUser.getImage());
		contact.setUserName(signUpUser.getUserName());
		

		/*contactSignUp.setSignUpId(contact.getSignUpId());
		contactSignUp.setImage(signUpUser.getImage());
		contactSignUp.setUserName(signUpUser.getUserName());
		contactSignUp.setPassword(contact.getPassword());
		contactSignUp.setPaid(contact.isPaid());
		contactSignUp.setPhoneNumber(contact.getPhoneNumber());
		contactSignUp.setEmailId(contact.getEmailId());*/

		dariAstroCRUD.methodForUpdate(contact);

		resultResponse.setResult("Profile Successfully Updated");
		resultResponse.setStatus("true");
		resultResponse.setProfileImage(signUpUser.getImage());
		resultResponse.setProfileName(signUpUser.getUserName());
		return resultResponse;

	}

	@Transactional
	public BirthChartResultResponse addBirthChart(BirthChartDetails birthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		birthChartDetails.setBirthChartAddedDate(dariTimeMappers.getCurrentSystemDateAndTime());
		dariAstroCRUD.methodForSave(birthChartDetails);
		birthChartResultResponse.setResult("BirthChart Successfully Saved");
		birthChartResultResponse.setStatus("true");
		birthChartResultResponse.setBirthChartDetails(getBirthChartById(birthChartDetails.getId()));
		return birthChartResultResponse;
	}
	
	@Transactional
	public BirthChartDetails getBirthChartById(int id) {
		BirthChartDetails birthChartDetails = null;
		try {
			birthChartDetails = (BirthChartDetails) dariAstroCRUD.getFromDb(BirthChartDetails.class, id);
		} catch (Exception e) {
			return null;
		}
		return birthChartDetails;
		
	}
	

	@Transactional
	public BirthChartResultResponse addMultipleBirthChart(AddMultipleBirthChartDetails addMultipleBirthChartDetails) {
		
		List<BirthChartDetails>  birthChartDetailsList = addMultipleBirthChartDetails.getBirthChartDetailsList();
		
		List<BirthChartDetails> birthChartDetailsListNew = new ArrayList<BirthChartDetails>();
		
		List<String> unaddedBirthCharts =new ArrayList<String>();
		
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();

		for (BirthChartDetails birthChartDetails : birthChartDetailsList) {
			BirthChartResultResponse birthChartResultResponse1 = addBirthChart(birthChartDetails);
			if (birthChartResultResponse1.getResult().equalsIgnoreCase("BirthChart Successfully Saved")) {
				birthChartDetailsListNew.add(birthChartResultResponse1.getBirthChartDetails());
			}else {
				unaddedBirthCharts.add(birthChartDetails.getBirth_ID());
			}
		}
		birthChartResultResponse.setStatus("true");
		birthChartResultResponse.setResult("BirthCharts Successfully Saved");
		birthChartResultResponse.setBirthChartDetailsList(birthChartDetailsListNew);
		birthChartResultResponse.setUnAddedbirthCharts(unaddedBirthCharts);
		return birthChartResultResponse;
	}
	

}
