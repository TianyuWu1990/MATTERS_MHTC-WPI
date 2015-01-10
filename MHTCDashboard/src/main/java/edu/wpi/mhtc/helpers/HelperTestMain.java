package edu.wpi.mhtc.helpers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelperTestMain {
	public static void main(String[] args) throws Exception {
    	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
    
       	Mailer mm = (Mailer) context.getBean("mailMail");
        mm.sendMail("hlong290494@gmail.com",
       		   "lhnguyenduc@wpi.edu",
       		   "Testing123", 
       		   "Testing only \n\n Hello Spring Email Sender");			
	}
	
}
