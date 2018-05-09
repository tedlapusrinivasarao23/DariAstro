package com.dari.astro.bos;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Birth_Chart")
@JsonIgnoreProperties
public class BirthChartDetails {

	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int id;

	private String birth_ID;

	@Column(nullable = false)
	private String ownerNumber;

	@Column(nullable = false)
	private String ownerEmail;

	@Column(name = "photo", unique = false, length = 100000)
	private byte[] photo;

	private String birth_Group;
	private String sur_Name;
	private String first_Name;
	private String gender;
	private String birth_Date;
	private String birth_Time;
	private String birth_Place;
	private String birth_City;
	private String birth_State;
	private String birth_Country;
	private double birth_Latitude;
	private double birth_Longitude;
	private String birth_UTC;
	private String birth_TimeZone;
	private String birth_DST_WT;
	private String birthChartAddedDate;
	private String birthChartUpdatedDate;

	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "Birth_Event", joinColumns = @JoinColumn(name = "Birth_ID"))
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name = "Event_ID") }, generator = "hilo-gen", type = @Type(type = "int"))
	private Collection<Events> events = new ArrayList<Events>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBirth_ID() {
		return birth_ID;
	}

	public void setBirth_ID(String birth_ID) {
		this.birth_ID = birth_ID;
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getBirth_Group() {
		return birth_Group;
	}

	public void setBirth_Group(String birth_Group) {
		this.birth_Group = birth_Group;
	}

	public String getSur_Name() {
		return sur_Name;
	}

	public void setSur_Name(String sur_Name) {
		this.sur_Name = sur_Name;
	}

	public String getFirst_Name() {
		return first_Name;
	}

	public void setFirst_Name(String first_Name) {
		this.first_Name = first_Name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth_Date() {
		return birth_Date;
	}

	public void setBirth_Date(String birth_Date) {
		this.birth_Date = birth_Date;
	}

	public String getBirth_Time() {
		return birth_Time;
	}

	public void setBirth_Time(String birth_Time) {
		this.birth_Time = birth_Time;
	}

	public String getBirth_Place() {
		return birth_Place;
	}

	public void setBirth_Place(String birth_Place) {
		this.birth_Place = birth_Place;
	}

	public String getBirth_City() {
		return birth_City;
	}

	public void setBirth_City(String birth_City) {
		this.birth_City = birth_City;
	}

	public String getBirth_State() {
		return birth_State;
	}

	public void setBirth_State(String birth_State) {
		this.birth_State = birth_State;
	}

	public String getBirth_Country() {
		return birth_Country;
	}

	public void setBirth_Country(String birth_Country) {
		this.birth_Country = birth_Country;
	}

	public double getBirth_Latitude() {
		return birth_Latitude;
	}

	public void setBirth_Latitude(double birth_Latitude) {
		this.birth_Latitude = birth_Latitude;
	}

	public double getBirth_Longitude() {
		return birth_Longitude;
	}

	public void setBirth_Longitude(double birth_Longitude) {
		this.birth_Longitude = birth_Longitude;
	}

	public String getBirth_UTC() {
		return birth_UTC;
	}

	public void setBirth_UTC(String birth_UTC) {
		this.birth_UTC = birth_UTC;
	}

	public String getBirth_TimeZone() {
		return birth_TimeZone;
	}

	public void setBirth_TimeZone(String birth_TimeZone) {
		this.birth_TimeZone = birth_TimeZone;
	}

	public String getBirth_DST_WT() {
		return birth_DST_WT;
	}

	public void setBirth_DST_WT(String birth_DST_WT) {
		this.birth_DST_WT = birth_DST_WT;
	}

	public String getBirthChartAddedDate() {
		return birthChartAddedDate;
	}

	public void setBirthChartAddedDate(String birthChartAddedDate) {
		this.birthChartAddedDate = birthChartAddedDate;
	}

	public String getBirthChartUpdatedDate() {
		return birthChartUpdatedDate;
	}

	public void setBirthChartUpdatedDate(String birthChartUpdatedDate) {
		this.birthChartUpdatedDate = birthChartUpdatedDate;
	}

	public Collection<Events> getEvents() {
		return events;
	}

	public void setEvents(Collection<Events> events) {
		this.events = events;
	}


}
