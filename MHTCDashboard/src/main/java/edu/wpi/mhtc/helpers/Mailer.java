package edu.wpi.mhtc.helpers;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class Mailer {
	private JavaMailSender mailSender;
	private String fromAddress = "hlong290494@gmail.com";
	public void setMailSender(JavaMailSender mailSender) {
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

	public void sendResetPasswordMail(String email, String unencryptedTokenString) throws MessagingException {
		String token = MD5.getMD5(unencryptedTokenString);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		String htmlMsg = "<h3>Hello!</h3> Here is the link to reset your password: <br> http://localhost:8080/mhtc/user/reset?token=" + token ;
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(email);
		helper.setSubject("MHTC - Password Reset Detail");
		helper.setFrom(fromAddress);
		mailSender.send(mimeMessage);		
	}
}
