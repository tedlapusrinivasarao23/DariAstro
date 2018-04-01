package com.dari.astro.bos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SignUpUser {
	
	
	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int signUpId;
	
	@Column(name = "image", unique = false, length = 100000)
	private byte[] image;

	@Column
	private String userName;

	@Column(unique = true)
	private String emailId;
	
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column
	private boolean paid=false;
	

	@Column
	private String password;

	@Column(unique = true)
	private String phoneNumber;
	
	@Column
	private String signupDateAndTime;
	
	@Column
	private String signupDeviceOS;
	
	@Column
	private String signupDeviceID;
	
	@Column
	private String signupLocation;
	
	@Column
	private String signupDeviceOSVersion;
	
	@Column
	private String signupDeviceModel;
	
	@Column
	private int subscriptionDaysLeft;
	
	@Column
	private String subscriptionEndDate;
	
	@Column
	private String subscribedDate;
	
	@Column
	private String isActive;

	public int getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(int signUpId) {
		this.signUpId = signUpId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSignupDateAndTime() {
		return signupDateAndTime;
	}

	public void setSignupDateAndTime(String signupDateAndTime) {
		this.signupDateAndTime = signupDateAndTime;
	}

	public String getSignupDeviceOS() {
		return signupDeviceOS;
	}

	public void setSignupDeviceOS(String signupDeviceOS) {
		this.signupDeviceOS = signupDeviceOS;
	}

	public String getSignupDeviceID() {
		return signupDeviceID;
	}

	public void setSignupDeviceID(String signupDeviceID) {
		this.signupDeviceID = signupDeviceID;
	}

	public String getSignupLocation() {
		return signupLocation;
	}

	public void setSignupLocation(String signupLocation) {
		this.signupLocation = signupLocation;
	}

	public String getSignupDeviceOSVersion() {
		return signupDeviceOSVersion;
	}

	public void setSignupDeviceOSVersion(String signupDeviceOSVersion) {
		this.signupDeviceOSVersion = signupDeviceOSVersion;
	}

	public String getSignupDeviceModel() {
		return signupDeviceModel;
	}

	public void setSignupDeviceModel(String signupDeviceModel) {
		this.signupDeviceModel = signupDeviceModel;
	}

	public int getSubscriptionDaysLeft() {
		return subscriptionDaysLeft;
	}

	public void setSubscriptionDaysLeft(int subscriptionDaysLeft) {
		this.subscriptionDaysLeft = subscriptionDaysLeft;
	}

	public String getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(String subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public String getSubscribedDate() {
		return subscribedDate;
	}

	public void setSubscribedDate(String subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	

}
