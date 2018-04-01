package com.dari.astro.bos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LoginUserDetails implements Cloneable {

	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int loginId;

	@Column
	private String userEmailId;
	@Column
	private String userPhoneNumber;
	@Column
	private String lastLoginTime;
	@Column
	private String lastLoginDeviceOS;
	@Column
	private String lastLoginDeviceID;
	
	@Column
	private String lastLoginDeviceOSVersion;
	@Column
	private String lastLoginDeviceModel;
	
	
	
	
	public String getLastLoginDeviceOSVersion() {
		return lastLoginDeviceOSVersion;
	}
	public void setLastLoginDeviceOSVersion(String lastLoginDeviceOSVersion) {
		this.lastLoginDeviceOSVersion = lastLoginDeviceOSVersion;
	}
	public String getLastLoginDeviceModel() {
		return lastLoginDeviceModel;
	}
	public void setLastLoginDeviceModel(String lastLoginDeviceModel) {
		this.lastLoginDeviceModel = lastLoginDeviceModel;
	}
	@Column
	private String lastLoginLocation;
	
	
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginDeviceOS() {
		return lastLoginDeviceOS;
	}
	public void setLastLoginDeviceOS(String lastLoginDeviceOS) {
		this.lastLoginDeviceOS = lastLoginDeviceOS;
	}
	public String getLastLoginDeviceID() {
		return lastLoginDeviceID;
	}
	public void setLastLoginDeviceID(String lastLoginDeviceID) {
		this.lastLoginDeviceID = lastLoginDeviceID;
	}
	public String getLastLoginLocation() {
		return lastLoginLocation;
	}
	public void setLastLoginLocation(String lastLoginLocation) {
		this.lastLoginLocation = lastLoginLocation;
	}
	
	
}
