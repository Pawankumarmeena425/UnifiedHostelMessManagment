package com.company.helper;

import java.util.Properties;

import com.google.protobuf.Message;

//
//import jakarta.mail.Authenticator;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class GEmailSender {
	public boolean sendEmail(String to, String from, String subject, String text) {
		boolean flag = false;

		// Logic

		// Smtp Properties ::
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		String username = "pawankuamrmeena9";
		String password = "zebc uawo ytbg xutj";

		// Getting Session
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}

		});

		try {
			javax.mail.Message message = new MimeMessage(session);
			message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

}
