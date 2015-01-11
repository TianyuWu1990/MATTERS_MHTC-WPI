package edu.wpi.mhtc.helpers;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class Mailer {
	private MailSender mailSender;
	private String fromAddress = "";
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMail(String to, String subject, String msg) {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(fromAddress);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
}
