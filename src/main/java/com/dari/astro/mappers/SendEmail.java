package com.dari.astro.mappers;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("sendEmail")
@PropertySource(value = { "classpath:application.properties" })
public class SendEmail {
	
	@Autowired
	private Environment environment;

	public boolean sendEmailForgotPassword(String toEmailId, String newPassword,String messageInData) {

		String to = toEmailId;
		final String from = environment.getProperty("from");//"ngomailtest@gmail.com";// change accordingly
		final String password =environment.getProperty("password"); //"ngo1234567890";// change accordingly

		// Get the session object
		Properties props = new Properties();
		
		props.put(environment.getProperty("host"), environment.getProperty("hostType"));
		props.put(environment.getProperty("factoryPort"), environment.getProperty("factoryPortType"));
		props.put(environment.getProperty("class"), environment.getProperty("classType"));
		props.put(environment.getProperty("auth"), "true");
		props.put(environment.getProperty("port"), environment.getProperty("portType"));
		
		/*props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");*/

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		// compose message
		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from,"NoReply-DariAstro"));
			message.setReplyTo(InternetAddress.parse(from, false));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("NGC Password Alert");
			message.setText(messageInData +newPassword);

			Transport.send(message); 
			return true;

		} catch (MessagingException|UnsupportedEncodingException e) {
			return false;
		}

	

	}

}
