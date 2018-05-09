package com.dari.astro.bos;

import javax.persistence.Embeddable;

@Embeddable
public class Events {
	
private String event_Name;
private String	event_Date;
private String	event_Time;
private String	event_Address;
private double	event_Latitude;
private double	event_Longitude;
private String	event_UTC;
private String	event_TZ;
private String	event_DST_WT;
private String	event_Notes;

private String eventAddedDate;
private String eventUpdatedDate;

public String getEvent_Name() {
	return event_Name;
}
public void setEvent_Name(String event_Name) {
	this.event_Name = event_Name;
}
public String getEvent_Date() {
	return event_Date;
}
public void setEvent_Date(String event_Date) {
	this.event_Date = event_Date;
}
public String getEvent_Time() {
	return event_Time;
}
public void setEvent_Time(String event_Time) {
	this.event_Time = event_Time;
}
public String getEvent_Address() {
	return event_Address;
}
public void setEvent_Address(String event_Address) {
	this.event_Address = event_Address;
}
public double getEvent_Latitude() {
	return event_Latitude;
}
public void setEvent_Latitude(double event_Latitude) {
	this.event_Latitude = event_Latitude;
}
public double getEvent_Longitude() {
	return event_Longitude;
}
public void setEvent_Longitude(double event_Longitude) {
	this.event_Longitude = event_Longitude;
}
public String getEvent_UTC() {
	return event_UTC;
}
public void setEvent_UTC(String event_UTC) {
	this.event_UTC = event_UTC;
}
public String getEvent_TZ() {
	return event_TZ;
}
public void setEvent_TZ(String event_TZ) {
	this.event_TZ = event_TZ;
}
public String getEvent_DST_WT() {
	return event_DST_WT;
}
public void setEvent_DST_WT(String event_DST_WT) {
	this.event_DST_WT = event_DST_WT;
}
public String getEvent_Notes() {
	return event_Notes;
}
public void setEvent_Notes(String event_Notes) {
	this.event_Notes = event_Notes;
}
public String getEventAddedDate() {
	return eventAddedDate;
}
public void setEventAddedDate(String eventAddedDate) {
	this.eventAddedDate = eventAddedDate;
}
public String getEventUpdatedDate() {
	return eventUpdatedDate;
}
public void setEventUpdatedDate(String eventUpdatedDate) {
	this.eventUpdatedDate = eventUpdatedDate;
}

}
