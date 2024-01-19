package com.logiclegends.MerryMeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	

	public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
	

	public void sendContactEmail(String to, String from, String subject, String text) {
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(from);  // Set the sender as the user-inputted email
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, to);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);
        };

        javaMailSender.send(preparator);
    }

	

}
