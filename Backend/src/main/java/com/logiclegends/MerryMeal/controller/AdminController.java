package com.logiclegends.MerryMeal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiclegends.MerryMeal.entities.PendingRegistration;
import com.logiclegends.MerryMeal.entities.UserRole;
import com.logiclegends.MerryMeal.service.RegistrationServiceImpl;

@RestController
@RequestMapping("/logic")
public class AdminController {

	
    @Autowired
    private RegistrationServiceImpl regServiceImpl;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/pending-registrations")
    public List<PendingRegistration> getPendingRegistrations() {
        // Return a list of pending registrations
        return regServiceImpl.retrievePendingUsers();
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/approve-registration/{id}")
	  public String approveRegistration(@PathVariable Long id) {  	
	      UserRole role = regServiceImpl.getRoleById(id);
			// Approve registration and move data to respective tables
	      regServiceImpl.approveRegistration(id,role);
	      return "User approved";
	  }

	
	//reject registration
	@PostMapping("/reject-registration/{id}")
	public ResponseEntity<String> rejectRegistration(@PathVariable Long id) {
		regServiceImpl.rejectRegistration(id);
		
		return ResponseEntity.ok("User rejected");
	}
}
