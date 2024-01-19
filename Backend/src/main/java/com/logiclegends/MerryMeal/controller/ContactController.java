package com.logiclegends.MerryMeal.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logiclegends.MerryMeal.service.ContactService;
import com.logiclegends.MerryMeal.service.EmailService;

@RestController
@RequestMapping("/logic")
public class ContactController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/contactUs")
	public ResponseEntity<String> sendContactInfo(@RequestParam String email, @RequestParam String message) {
	    String subject = "Feedback";
	    String to = "logiclegends.mow.5@gmail.com";
	    String from = email;  // Use the user's inputted email as the sender

	    try {
	        emailService.sendContactEmail(to, from, subject, message);
	        return ResponseEntity.ok("Form submitted successfully!");
	    } catch (MailException e) {
	        // Log the exception or handle it accordingly
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
	    }
	}

}
