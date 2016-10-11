package uta.mav.appoint;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;

public class NotifyRegistration {

	public static void sendRegistrationDetails(String studentEmail, String password, String expiry) {
		try {
			String to = studentEmail;
			String subject = "Your MavAppoint login details";
			String body = "User Name: " + studentEmail + "\nPassword: "+password+"\n\n Your password will expire in "+expiry+ "hrs. Please change your password.";
			String from = "maverickappointments@gmail.com";
			String pw = "gue#212!ns";
			String host = "smtp.gmail.com";
			String port = "465";
			Properties properties = System.getProperties();
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.user", from);
			properties.put("mail.smtp.password", pw);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getDefaultInstance(properties,
					new javax.mail.Authenticator(){
						protected PasswordAuthentication getPasswordAuthentication(){
							return new PasswordAuthentication("maverickappointments@gmail.com","gue#212!ns");
						}
			});
			
			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setText(body);
			message.setSubject(subject);
			Transport.send(message);
			System.out.println("Message sent successfully.");
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}
}