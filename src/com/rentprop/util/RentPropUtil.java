package com.rentprop.util;

import java.util.Random;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RentPropUtil {

	public RentPropUtil() {
		// TODO Auto-generated constructor stub
	}
	public static int generateOTP(int limit) {
		int otp = 0;
		if (limit > 5 || limit < 1)
			limit = 3;
		for (int i = 0; i < limit; i++) {
			int x = new Random().nextInt(9);
			if (x == 0 && i == 0)
				i--;
			else
				otp = (otp * 10) + x;
		}
		return otp;
	}
	public static void sendMail(String sentToAddress, String subject, String emailMsg) {

		java.util.Properties properties = new java.util.Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		javax.mail.Session mailSession = javax.mail.Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(mailSession);
			
			message.setContent(emailMsg, "text/html");
			message.setSubject(subject);

			InternetAddress sender = new InternetAddress("rentpropmgr@gmail.com", "Mail");
			InternetAddress receiver = new InternetAddress(sentToAddress);
			message.setFrom(sender);
			message.setRecipient(Message.RecipientType.TO, receiver);
			message.saveChanges();

			javax.mail.Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.mailtrap.io", 2525, "3cfe73fb6654d3", "3b2092275eb3ad");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		sendMail("kk@mailinator.com", 12312);
	}
}
