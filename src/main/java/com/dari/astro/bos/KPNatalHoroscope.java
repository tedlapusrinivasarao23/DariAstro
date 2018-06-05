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
@Table(name = "KPNatalHoroscope")
@JsonIgnoreProperties
public class KPNatalHoroscope {
	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int id;
	
	@Column(nullable = false)
	private String ownerNumber;

	@Column(nullable = false)
	private String ownerEmail;
	
	private String transaction_Id;
	private String transaction_Amount;
	private String transaction_Status;
	private String transaction_Device_location;
	private String transaction_Device_OS;
	private String transaction_Device_ID;
	private String transaction_Device_Model;
	private String transaction_Date;
	private String subscription_Date;
	private String subscription_EndDate;
	private int subscription_Days_Left;
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column
	private boolean chartStatus=false;
	private String chartName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getTransaction_Id() {
		return transaction_Id;
	}
	public void setTransaction_Id(String transaction_Id) {
		this.transaction_Id = transaction_Id;
	}
	public String getTransaction_Amount() {
		return transaction_Amount;
	}
	public void setTransaction_Amount(String transaction_Amount) {
		this.transaction_Amount = transaction_Amount;
	}
	public String getTransaction_Status() {
		return transaction_Status;
	}
	public void setTransaction_Status(String transaction_Status) {
		this.transaction_Status = transaction_Status;
	}
	public String getTransaction_Device_location() {
		return transaction_Device_location;
	}
	public void setTransaction_Device_location(String transaction_Device_location) {
		this.transaction_Device_location = transaction_Device_location;
	}
	public String getTransaction_Device_OS() {
		return transaction_Device_OS;
	}
	public void setTransaction_Device_OS(String transaction_Device_OS) {
		this.transaction_Device_OS = transaction_Device_OS;
	}
	public String getTransaction_Device_ID() {
		return transaction_Device_ID;
	}
	public void setTransaction_Device_ID(String transaction_Device_ID) {
		this.transaction_Device_ID = transaction_Device_ID;
	}
	public String getTransaction_Device_Model() {
		return transaction_Device_Model;
	}
	public void setTransaction_Device_Model(String transaction_Device_Model) {
		this.transaction_Device_Model = transaction_Device_Model;
	}
	public String getTransaction_Date() {
		return transaction_Date;
	}
	public void setTransaction_Date(String transaction_Date) {
		this.transaction_Date = transaction_Date;
	}
	public String getSubscription_Date() {
		return subscription_Date;
	}
	public void setSubscription_Date(String subscription_Date) {
		this.subscription_Date = subscription_Date;
	}
	public String getSubscription_EndDate() {
		return subscription_EndDate;
	}
	public void setSubscription_EndDate(String subscription_EndDate) {
		this.subscription_EndDate = subscription_EndDate;
	}

	public int getSubscription_Days_Left() {
		return subscription_Days_Left;
	}
	public void setSubscription_Days_Left(int subscription_Days_Left) {
		this.subscription_Days_Left = subscription_Days_Left;
	}
	public boolean isChartStatus() {
		return chartStatus;
	}
	public void setChartStatus(boolean chartStatus) {
		this.chartStatus = chartStatus;
	}
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	

}
