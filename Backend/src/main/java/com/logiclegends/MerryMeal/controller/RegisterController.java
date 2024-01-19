package com.logiclegends.MerryMeal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.logiclegends.MerryMeal.entities.AdminEntity;
import com.logiclegends.MerryMeal.entities.PendingRegistration;
import com.logiclegends.MerryMeal.service.RegistrationServiceImpl;

@RestController
@RequestMapping("/logic")
public class RegisterController {
	   
    @Autowired
    private RegistrationServiceImpl regServiceImpl;
    
    @Autowired
	private PasswordEncoder passwordEncoder;   
	
    @PostMapping(value="/adminReg")
    public String registerAdmin(@RequestBody AdminEntity admin) {
    	admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
    	regServiceImpl.registerAdmin(admin);
		return "Admin successfully registered";
    	
    }
	@PostMapping(value="/memberReg")
	public String registerMember(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("evidenceImage") MultipartFile file){
		
		 PendingRegistration pendingUser = new PendingRegistration();
	        pendingUser.setName(name);
	        pendingUser.setEmail(email);
	        pendingUser.setPassword(passwordEncoder.encode(password));
		
		try {
			regServiceImpl.savePendingMember(pendingUser, file);
			return "Wait for the Admin's approval";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occurred during registration";
		}	
	}
	
	
	@PostMapping(value="/volunteerReg")
	public String registerVolunteer(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("evidenceImage") MultipartFile file){
		
		PendingRegistration pendingUser = new PendingRegistration();
		pendingUser.setName(name);
        pendingUser.setEmail(email);
		pendingUser.setPassword(passwordEncoder.encode(password));
		
	
		
		try {
			regServiceImpl.savePendingVolunteer(pendingUser, file);
			return "Wait for the Admin's approval";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occurred during registration";
		}
		
	}
	
	@PostMapping(value="/partnerReg")
	public String registerPartner(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("lat") double latitude,@RequestParam("long") double longitude,@RequestParam("evidenceImage") MultipartFile file){
		
		PendingRegistration pendingUser = new PendingRegistration();
		pendingUser.setName(name);
        pendingUser.setEmail(email);
        pendingUser.setLatitude(latitude);
        pendingUser.setLongitude(longitude);
        
		pendingUser.setPassword(passwordEncoder.encode(password));
		
		
		try {
			regServiceImpl.savePendingPartner(pendingUser, file);
			return "Wait for the Admin's approval";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occurred during registration";
		}
		
	}
	
	

}
