package com.dari.astro.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.dari.astro.bos.LogoutContactDetails;
import com.dari.astro.bos.MundaneAstrology;
import com.dari.astro.bos.SignUpUser;
import com.dari.astro.bos.TransactionHistory;
import com.dari.astro.bos.Varshphal;
import com.dari.astro.bos.VedicHoroscope;
import com.dari.astro.bos.VedicHoroscopePredictions;
import com.dari.astro.bos.VedicMuhurta;
import com.dari.astro.bos.VedicPanchanga;
import com.dari.astro.mappers.ChartTransactionMapper;
import com.dari.astro.mappers.DariTimeMappers;
import com.dari.astro.mappers.SendEmail;
import com.dari.astro.mappers.SendSms;
import com.dari.astro.mappers.TransactionHistoryMapper;
import com.dari.astro.repo.DariAstroCRUD;
import com.dari.astro.repo.DariAstroRepo;
import com.dari.astro.utils.AddMultipleBirthChartDetails;
import com.dari.astro.utils.BirthChartComparator;
import com.dari.astro.utils.BirthChartResultResponse;
import com.dari.astro.utils.DeleteMultipleBirthCharts;
import com.dari.astro.utils.ForgotPassword;
import com.dari.astro.utils.LoginResponse;
import com.dari.astro.utils.LoginUser;
import com.dari.astro.utils.LogoutResponse;
import com.dari.astro.utils.LogoutUser;
import com.dari.astro.utils.ResultResponse;
import com.dari.astro.utils.TransactionBean;
import com.dari.astro.utils.TransactionResponse;
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

	@Autowired
	private ChartTransactionMapper chartTransactionMapper;
	
	@Autowired
	private TransactionHistoryMapper transactionHistoryMapper;
	
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
					&& signUpUserList.getIsActive().equalsIgnoreCase("NO"))
					|| ((signUpUserList.getPassword().equals(loginUser.getPassword())
							&& (signUpUserList.getSignupDeviceID().equalsIgnoreCase(loginUser.getLoginDeviceID()))))) {

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

		/*
		 * contactSignUp.setSignUpId(contact.getSignUpId());
		 * contactSignUp.setImage(signUpUser.getImage());
		 * contactSignUp.setUserName(signUpUser.getUserName());
		 * contactSignUp.setPassword(contact.getPassword());
		 * contactSignUp.setPaid(contact.isPaid());
		 * contactSignUp.setPhoneNumber(contact.getPhoneNumber());
		 * contactSignUp.setEmailId(contact.getEmailId());
		 */

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

		List<BirthChartDetails> birthChartDetailsList = addMultipleBirthChartDetails.getBirthChartDetailsList();

		List<BirthChartDetails> birthChartDetailsListNew = new ArrayList<BirthChartDetails>();

		List<String> unaddedBirthCharts = new ArrayList<String>();

		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();

		for (BirthChartDetails birthChartDetails : birthChartDetailsList) {
			BirthChartResultResponse birthChartResultResponse1 = addBirthChart(birthChartDetails);
			if (birthChartResultResponse1.getResult().equalsIgnoreCase("BirthChart Successfully Saved")) {
				birthChartDetailsListNew.add(birthChartResultResponse1.getBirthChartDetails());
			} else {
				unaddedBirthCharts.add(birthChartDetails.getBirth_ID());
			}
		}
		birthChartResultResponse.setStatus("true");
		birthChartResultResponse.setResult("BirthCharts Successfully Saved");
		birthChartResultResponse.setBirthChartDetailsList(birthChartDetailsListNew);
		birthChartResultResponse.setUnAddedbirthCharts(unaddedBirthCharts);
		return birthChartResultResponse;
	}

	@Transactional
	public BirthChartResultResponse editBirthChart(BirthChartDetails birthChartDetails) {
		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		try {
			birthChartDetails.setBirthChartUpdatedDate(dariTimeMappers.getCurrentSystemDateAndTime());
			dariAstroCRUD.methodForUpdate(birthChartDetails);
			birthChartResultResponse.setStatus("true");
			birthChartResultResponse.setResult("BirthChart Updated Sucessfully");
			return birthChartResultResponse;
		} catch (Exception e) {
			birthChartResultResponse.setStatus("false");
			birthChartResultResponse.setResult("BirthChart Not Updated");
			return birthChartResultResponse;
		}

	}

	@Transactional
	public BirthChartResultResponse editMultipleBirthChart(AddMultipleBirthChartDetails addMultipleBirthChartDetails) {

		BirthChartResultResponse birthChartResultResponse = new BirthChartResultResponse();
		List<BirthChartDetails> birthChartDetailsListNew = new ArrayList<BirthChartDetails>();

		List<String> unEditedBirthCharts = new ArrayList<String>();

		List<BirthChartDetails> birthChartDetailsList = addMultipleBirthChartDetails.getBirthChartDetailsList();

		for (BirthChartDetails birthChartDetailsLocal : birthChartDetailsList) {
			birthChartResultResponse = editBirthChart(birthChartDetailsLocal);
			if (birthChartResultResponse.getResult().equalsIgnoreCase("BirthChart Updated Sucessfully")) {
				birthChartDetailsListNew.add(birthChartResultResponse.getBirthChartDetails());
			} else {
				unEditedBirthCharts.add(birthChartDetailsLocal.getBirth_ID());
			}
		}
		birthChartResultResponse.setStatus("true");
		birthChartResultResponse.setResult("BirthCharts Updated Sucessfully");
		birthChartResultResponse.setBirthChartDetailsList(birthChartDetailsListNew);
		birthChartResultResponse.setUnEditedBirthCharts(unEditedBirthCharts);
		return birthChartResultResponse;
	}

	@Transactional
	public ResultResponse deleteBirthChart(String ownerNumber, String ownerEmail, int birthChartid) {
		ResultResponse resultResponse = new ResultResponse();
		try {
			BirthChartDetails birthChartDetails = dariAstroRepo.getBirthChartByNumberAndEmailAndId(ownerNumber,
					ownerEmail, birthChartid);
			dariAstroCRUD.methodForDelete(birthChartDetails);

			resultResponse.setStatus("true");
			resultResponse.setResult("BirthChart Deleted Successfully");
			return resultResponse;
		} catch (Exception e) {
			resultResponse.setStatus("false");
			resultResponse.setResult("BirthChart Not Deleted due to no BirthChart exists with id" + birthChartid);
			return resultResponse;
		}
	}

	@Transactional
	public ResultResponse deleteMultipleBirthChart(DeleteMultipleBirthCharts deleteMultipleBirthCharts) {
		ResultResponse response = new ResultResponse();
		String ownerNumber = deleteMultipleBirthCharts.getOwnerNumber();
		String ownerEmail = deleteMultipleBirthCharts.getOwnerEmail();
		List<Integer> birthChartIds = deleteMultipleBirthCharts.getBirthChartIds();

		List<BirthChartDetails> birthChartDetailslist = dariAstroCRUD.loadAll(birthChartIds);
		dariAstroCRUD.methodForDeleteAll(birthChartDetailslist);

		for (Integer birthChartid : birthChartIds) {
			deleteBirthChart(ownerNumber, ownerEmail, birthChartid);
		}
		response.setStatus("true");
		response.setResult("BirthCharts Deleted Successfully");
		return response;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<BirthChartDetails> getBirtchartList(String ownerNumber, String ownerEmail) {
		List<BirthChartDetails> birthChartDetailsList = null;
		try {
			birthChartDetailsList = dariAstroRepo.getBirthChartByOwnerNumberOrOwnerEmail(ownerNumber, ownerEmail);

			Collections.sort(birthChartDetailsList, new BirthChartComparator());
		} catch (Exception e) {
			return null;
		}
		return birthChartDetailsList;
	}

	@Transactional
	public BirthChartDetails getBirtchartById(String ownerNumber, String ownerEmail, int id) {
		BirthChartDetails birthChartDetails = null;
		try {
			birthChartDetails = (BirthChartDetails) dariAstroCRUD.getFromDb(BirthChartDetails.class, id);
		} catch (Exception e) {
			return null;
		}
		return birthChartDetails;
	}

	@Transactional
	public TransactionResponse kpNatalHoroscopeTrans(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPNatalHoroscope kpNatalHoroscope=new KPNatalHoroscope();
		
		
			try {
				 kpNatalHoroscope= dariAstroRepo.getKPNatalHoroscopeByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpNatalHoroscope.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
					kpNatalHoroscope.setChartName(transactionBean.getChartName());
					kpNatalHoroscope.setOwnerEmail(transactionBean.getOwnerEmail());
					kpNatalHoroscope.setOwnerNumber(transactionBean.getOwnerNumber());
					kpNatalHoroscope.setTransaction_Id(transactionBean.getTransaction_Id());
					kpNatalHoroscope.setTransaction_Amount(transactionBean.getTransaction_Amount());
					kpNatalHoroscope.setTransaction_Status(transactionBean.getTransaction_Status());
					kpNatalHoroscope.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
					kpNatalHoroscope.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
					kpNatalHoroscope.setTransaction_Device_ID(transactionBean.getTransaction_Id());
					kpNatalHoroscope.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
					kpNatalHoroscope.setChartStatus(true);
					kpNatalHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					kpNatalHoroscope.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					kpNatalHoroscope.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
					kpNatalHoroscope.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpNatalHoroscope);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscope.getTransaction_Date(),kpNatalHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpNatalHoroscope.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpNatalHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscope.getTransaction_Date(),kpNatalHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					dariAstroCRUD.methodForSave(kpNatalHoroscope);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscope.getTransaction_Date(),kpNatalHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpNatalHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscope.getTransaction_Date(),kpNatalHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse kpHoraryHoroscope(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPHoraryHoroscope kpHoraryHoroscope=new KPHoraryHoroscope();
		
		
			try {
				kpHoraryHoroscope= dariAstroRepo.getkpHoraryHoroscopeByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpHoraryHoroscope.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
					kpHoraryHoroscope.setChartName(transactionBean.getChartName());
					kpHoraryHoroscope.setOwnerEmail(transactionBean.getOwnerEmail());
					kpHoraryHoroscope.setOwnerNumber(transactionBean.getOwnerNumber());
					kpHoraryHoroscope.setTransaction_Id(transactionBean.getTransaction_Id());
					kpHoraryHoroscope.setTransaction_Amount(transactionBean.getTransaction_Amount());
					kpHoraryHoroscope.setTransaction_Status(transactionBean.getTransaction_Status());
					kpHoraryHoroscope.setTransaction_Device_location(transactionBean.getTransaction_Device_location());
					kpHoraryHoroscope.setTransaction_Device_OS(transactionBean.getTransaction_Device_OS());
					kpHoraryHoroscope.setTransaction_Device_ID(transactionBean.getTransaction_Id());
					kpHoraryHoroscope.setTransaction_Device_Model(transactionBean.getTransaction_Device_Model());
					kpHoraryHoroscope.setChartStatus(true);
					kpHoraryHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					kpHoraryHoroscope.setSubscription_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					kpHoraryHoroscope.setSubscription_EndDate(dariTimeMappers.getOneYearLaterCurrentSystemDateAndTime());
					kpHoraryHoroscope.setSubscription_Days_Left((int)dariTimeMappers.daysBetween());
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpHoraryHoroscope);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscope.getTransaction_Date(),kpHoraryHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpHoraryHoroscope.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpHoraryHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscope.getTransaction_Date(),kpHoraryHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpHoraryHoroscope=chartTransactionMapper.getMappedkpHoraryHoroscope(transactionBean);
					dariAstroCRUD.methodForSave(kpHoraryHoroscope);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscope.getTransaction_Date(),kpHoraryHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpHoraryHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscope.getTransaction_Date(),kpHoraryHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	
	@Transactional
	public TransactionResponse kpNatalHoroscopePredictions(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPNatalHoroscopePredictions kpNatalHoroscopePredictions=new KPNatalHoroscopePredictions();
		
		
			try {
				kpNatalHoroscopePredictions= dariAstroRepo.getkpNatalHoroscopePredictionsByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpNatalHoroscopePredictions.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpNatalHoroscopePredictions);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscopePredictions.getTransaction_Date(),kpNatalHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpNatalHoroscopePredictions.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpNatalHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscopePredictions.getTransaction_Date(),kpNatalHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpNatalHoroscopePredictions=chartTransactionMapper.getMappedkpNatalHoroscopePredictions(transactionBean);
					dariAstroCRUD.methodForSave(kpNatalHoroscopePredictions);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscopePredictions.getTransaction_Date(),kpNatalHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpNatalHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpNatalHoroscopePredictions.getTransaction_Date(),kpNatalHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse kpHoraryHoroscopePredictions(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPHoraryHoroscopePredictions kpHoraryHoroscopePredictions=new KPHoraryHoroscopePredictions();
		
		
			try {
				kpHoraryHoroscopePredictions= dariAstroRepo.getkpHoraryHoroscopePredictionsByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpHoraryHoroscopePredictions.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpHoraryHoroscopePredictions);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscopePredictions.getTransaction_Date(),kpHoraryHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpHoraryHoroscopePredictions.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpHoraryHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscopePredictions.getTransaction_Date(),kpHoraryHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpHoraryHoroscopePredictions=chartTransactionMapper.getMappedkpHoraryHoroscopePredictions(transactionBean);
					dariAstroCRUD.methodForSave(kpHoraryHoroscopePredictions);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscopePredictions.getTransaction_Date(),kpHoraryHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpHoraryHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoraryHoroscopePredictions.getTransaction_Date(),kpHoraryHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse kpEphemeris(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPEphemeris kpEphemeris=new KPEphemeris();
		
		
			try {
				kpEphemeris= dariAstroRepo.getkpEphemerisByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpEphemeris.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpEphemeris);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpEphemeris.getTransaction_Date(),kpEphemeris.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpEphemeris.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpEphemeris.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpEphemeris.getTransaction_Date(),kpEphemeris.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpEphemeris=chartTransactionMapper.getMappedkpEphemeris(transactionBean);
					dariAstroCRUD.methodForSave(kpEphemeris);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpEphemeris.getTransaction_Date(),kpEphemeris.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpEphemeris.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpEphemeris.getTransaction_Date(),kpEphemeris.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse kpMuhurta(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPMuhurta kpMuhurta=new KPMuhurta();
		
		
			try {
				kpMuhurta= dariAstroRepo.getkpMuhurtaByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpMuhurta.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpMuhurta);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpMuhurta.getTransaction_Date(),kpMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpMuhurta.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpMuhurta.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpMuhurta.getTransaction_Date(),kpMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpMuhurta=chartTransactionMapper.getMappedkpMuhurta(transactionBean);
					dariAstroCRUD.methodForSave(kpMuhurta);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpMuhurta.getTransaction_Date(),kpMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpMuhurta.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpMuhurta.getTransaction_Date(),kpMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse kpHoroscopeMatching(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		KPHoroscopeMatching kpHoroscopeMatching=new KPHoroscopeMatching();
		
		
			try {
				kpHoroscopeMatching= dariAstroRepo.getkpHoroscopeMatchingByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (kpHoroscopeMatching.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(kpHoroscopeMatching);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoroscopeMatching.getTransaction_Date(),kpHoroscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(kpHoroscopeMatching.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpHoroscopeMatching.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoroscopeMatching.getTransaction_Date(),kpHoroscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					kpHoroscopeMatching=chartTransactionMapper.getMappedkpHoroscopeMatching(transactionBean);
					dariAstroCRUD.methodForSave(kpHoroscopeMatching);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoroscopeMatching.getTransaction_Date(),kpHoroscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					kpHoroscopeMatching.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(kpHoroscopeMatching.getTransaction_Date(),kpHoroscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse vedicHoroscope(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		VedicHoroscope vedicHoroscope=new VedicHoroscope();
		
		
			try {
				vedicHoroscope= dariAstroRepo.getvedicHoroscopeByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (vedicHoroscope.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(vedicHoroscope);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscope.getTransaction_Date(),vedicHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(vedicHoroscope.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscope.getTransaction_Date(),vedicHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					vedicHoroscope=chartTransactionMapper.getMappedvedicHoroscope(transactionBean);
					dariAstroCRUD.methodForSave(vedicHoroscope);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscope.getTransaction_Date(),vedicHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicHoroscope.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscope.getTransaction_Date(),vedicHoroscope.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse vedicHoroscopePredictions(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		VedicHoroscopePredictions vedicHoroscopePredictions=new VedicHoroscopePredictions();
		
		
			try {
				vedicHoroscopePredictions= dariAstroRepo.getvedicHoroscopePredictionsByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (vedicHoroscopePredictions.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(vedicHoroscopePredictions);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscopePredictions.getTransaction_Date(),vedicHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(vedicHoroscopePredictions.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscopePredictions.getTransaction_Date(),vedicHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					vedicHoroscopePredictions=chartTransactionMapper.getMappedvedicHoroscopePredictions(transactionBean);
					dariAstroCRUD.methodForSave(vedicHoroscopePredictions);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscopePredictions.getTransaction_Date(),vedicHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicHoroscopePredictions.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicHoroscopePredictions.getTransaction_Date(),vedicHoroscopePredictions.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse vedicMuhurta(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		VedicMuhurta vedicMuhurta=new VedicMuhurta();
		
		
			try {
				vedicMuhurta= dariAstroRepo.getvedicMuhurtaByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (vedicMuhurta.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(vedicMuhurta);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicMuhurta.getTransaction_Date(),vedicMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(vedicMuhurta.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicMuhurta.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicMuhurta.getTransaction_Date(),vedicMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					vedicMuhurta=chartTransactionMapper.getMappedvedicMuhurta(transactionBean);
					dariAstroCRUD.methodForSave(vedicMuhurta);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicMuhurta.getTransaction_Date(),vedicMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicMuhurta.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicMuhurta.getTransaction_Date(),vedicMuhurta.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse vedicPanchanga(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		VedicPanchanga vedicPanchanga=new VedicPanchanga();
		
		
			try {
				vedicPanchanga= dariAstroRepo.getvedicPanchangaByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (vedicPanchanga.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(vedicPanchanga);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicPanchanga.getTransaction_Date(),vedicPanchanga.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(vedicPanchanga.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicPanchanga.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicPanchanga.getTransaction_Date(),vedicPanchanga.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					vedicPanchanga=chartTransactionMapper.getMappedvedicPanchanga(transactionBean);
					dariAstroCRUD.methodForSave(vedicPanchanga);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicPanchanga.getTransaction_Date(),vedicPanchanga.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					vedicPanchanga.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(vedicPanchanga.getTransaction_Date(),vedicPanchanga.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse horoscopeMatching(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		HoroscopeMatching horoscopeMatching=new HoroscopeMatching();
		
		
			try {
				horoscopeMatching= dariAstroRepo.gethoroscopeMatchingByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (horoscopeMatching.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(horoscopeMatching);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(horoscopeMatching.getTransaction_Date(),horoscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(horoscopeMatching.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					horoscopeMatching.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(horoscopeMatching.getTransaction_Date(),horoscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					horoscopeMatching=chartTransactionMapper.getMappedhoroscopeMatching(transactionBean);
					dariAstroCRUD.methodForSave(horoscopeMatching);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(horoscopeMatching.getTransaction_Date(),horoscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					horoscopeMatching.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(horoscopeMatching.getTransaction_Date(),horoscopeMatching.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse mundaneAstrology(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		MundaneAstrology mundaneAstrology=new MundaneAstrology();
		
		
			try {
				mundaneAstrology= dariAstroRepo.getmundaneAstrologyByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (mundaneAstrology.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(mundaneAstrology);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(mundaneAstrology.getTransaction_Date(),mundaneAstrology.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(mundaneAstrology.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					mundaneAstrology.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(mundaneAstrology.getTransaction_Date(),mundaneAstrology.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					mundaneAstrology=chartTransactionMapper.getMappedmundaneAstrology(transactionBean);
					dariAstroCRUD.methodForSave(mundaneAstrology);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(mundaneAstrology.getTransaction_Date(),mundaneAstrology.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					mundaneAstrology.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(mundaneAstrology.getTransaction_Date(),mundaneAstrology.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}

	@Transactional
	public TransactionResponse varshphal(String chartName, TransactionBean transactionBean) {

		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionHistory transactionHistory=null;
		Varshphal varshphal=new Varshphal();
		
		
			try {
				varshphal= dariAstroRepo.getvarshphalByEmailIdAndPhoneNumber(
						transactionBean.getOwnerEmail(), transactionBean.getOwnerNumber());
				if (varshphal.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					
					
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
					
					
					//kpNatalHoroscope=chartTransactionMapper.getMappedKPNatalHoroscope(kpNatalHoroscope, transactionBean);
					//kpNatalHoroscope.setId(kpNatalHoroscopeLocal.getId());
					dariAstroCRUD.methodForUpdate(varshphal);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(varshphal.getTransaction_Date(),varshphal.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("UPDATED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(varshphal.getChartName().equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					varshphal.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(varshphal.getTransaction_Date(),varshphal.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}

			} catch (Exception e) {
				if (chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("SUCCESS")) {
					varshphal=chartTransactionMapper.getMappedvarshphal(transactionBean);
					dariAstroCRUD.methodForSave(varshphal);
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(varshphal.getTransaction_Date(),varshphal.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}else if(chartName.equalsIgnoreCase(transactionBean.getChartName())
						&& transactionBean.getTransaction_Status().equalsIgnoreCase("FAILURE")) {
					varshphal.setTransaction_Date(dariTimeMappers.getCurrentSystemDateAndTime());
					transactionHistory=transactionHistoryMapper.mapTransactionHistory(varshphal.getTransaction_Date(),varshphal.getId(), transactionBean);
					dariAstroCRUD.methodForSave(transactionHistory);
					transactionResponse.setResult("SAVED IN HISTORY");
					transactionResponse.setStatus("SUCCESS");
					return transactionResponse; 
				}
			}
			transactionResponse.setResult("IN VALID STATUS");
			transactionResponse.setStatus("FAIL");
			return transactionResponse; 
	}
	
	
	
	
	
}
