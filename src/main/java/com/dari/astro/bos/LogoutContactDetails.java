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
public class LogoutContactDetails implements Cloneable{
	

	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int logoutId;
	
	@Column
	private String userEmailId ;
	@Column
	private String userPhoneNumber;
	@Column
	private String logoutDeviceOSHistory;
	@Column
	private String logoutDeviceIDHistory;
	@Column
	private String logoutLocationHistory;
	
	@Column
	private String logoutDeviceOSVersion;
	@Column
	private String logoutDeviceModel;
	
	@Column
	private String logoutTimeAndDateHistory;
	
	
	
	
	public String getLogoutDeviceOSVersion() {
		return logoutDeviceOSVersion;
	}
	public void setLogoutDeviceOSVersion(String logoutDeviceOSVersion) {
		this.logoutDeviceOSVersion = logoutDeviceOSVersion;
	}
	public String getLogoutDeviceModel() {
		return logoutDeviceModel;
	}
	public void setLogoutDeviceModel(String logoutDeviceModel) {
		this.logoutDeviceModel = logoutDeviceModel;
	}
	public String getLogoutTimeAndDateHistory() {
		return logoutTimeAndDateHistory;
	}
	public void setLogoutTimeAndDateHistory(String logoutTimeAndDateHistory) {
		this.logoutTimeAndDateHistory = logoutTimeAndDateHistory;
	}
	
	public int getLogoutId() {
		return logoutId;
	}
	public void setLogoutId(int logoutId) {
		this.logoutId = logoutId;
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
	public String getLogoutDeviceOSHistory() {
		return logoutDeviceOSHistory;
	}
	public void setLogoutDeviceOSHistory(String logoutDeviceOSHistory) {
		this.logoutDeviceOSHistory = logoutDeviceOSHistory;
	}
	public String getLogoutDeviceIDHistory() {
		return logoutDeviceIDHistory;
	}
	public void setLogoutDeviceIDHistory(String logoutDeviceIDHistory) {
		this.logoutDeviceIDHistory = logoutDeviceIDHistory;
	}
	public String getLogoutLocationHistory() {
		return logoutLocationHistory;
	}
	public void setLogoutLocationHistory(String logoutLocationHistory) {
		this.logoutLocationHistory = logoutLocationHistory;
	}
	
	


}
