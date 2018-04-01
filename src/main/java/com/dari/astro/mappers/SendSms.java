package com.dari.astro.mappers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("sendSms")
@PropertySource(value = { "classpath:application.properties" })
public class SendSms {
	
	@Autowired
	private Environment environment;
	
	public boolean sendSMS(String ownerNumber,String password,String msg){
		
		try{
		String postData="";
		String retval = "";

		String User =environment.getProperty("sms.userName");
		String passwd = environment.getProperty("sms.password");
		String mobilenumber = ""; 
		String message = msg+password;
		String sid = environment.getProperty("sms.sid");;
		
		postData += "username=" + URLEncoder.encode(User,"UTF-8") + "&password=" + passwd + "&to=" + ownerNumber + "&message=" + URLEncoder.encode(message,"UTF-8") + "&from=" + sid + "&sms_type=2";
		URL url = new URL(environment.getProperty("sms.url"));
		HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
		urlconnection.setRequestMethod("POST");
		urlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		urlconnection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
		out.write(postData);
		out.close();
		BufferedReader in = new BufferedReader(	new InputStreamReader(urlconnection.getInputStream()));
		String decodedString;
		while ((decodedString = in.readLine()) != null) {
			retval += decodedString;
		}
		in.close();

	return true;
	}catch(Exception e){
		return false;
	}
	}

}
