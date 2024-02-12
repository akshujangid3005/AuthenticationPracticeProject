package com.authtentication.service.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {
	
	public boolean sendEmail(String subject , String message,String to) {
		
		boolean f= true;
		
		String from= "jangidakshu55@gmail.com";
		
		String host="smtp.gmail.com";
		
		Properties properties=System.getProperties();	
		System.out.println("PROPERTIES "+ properties);
		
		properties.put("mail.smtp.host",host );
		properties.put("mail.smtp.port",587 ); // Change port to 587 for TLS
		properties.put("mail.smtp.starttls.enable","true" );
		properties.put("mail.smtp.auth","true" );
		
		Session session=Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication("techakshayjangid@gmail.com", "kthu keyd obcx qjrw");
			}
			
		});
		
		MimeMessage m    =		new MimeMessage(session);
         
         try {
        	 m.setFrom(from);
        	 
        	 m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        	 
        	 m.setSubject(subject);
        	 m.setContent(message, "text/html"); // Set message content as HTML
        	 
        	 Transport.send(m);
        	 
        	 System.out.println("send succsess........................");
         }catch (Exception e) {
			e.printStackTrace();
		}
		return f;
		
	}

}
