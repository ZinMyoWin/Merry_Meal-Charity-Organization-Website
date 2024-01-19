package com.logiclegends.MerryMeal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiclegends.MerryMeal.entities.CaregiverRequest;
import com.logiclegends.MerryMeal.service.CaregiverRequestService;
import com.logiclegends.MerryMeal.service.UserPrincipal;

@RestController
@RequestMapping("/logic")
public class CaregiverRequestController {

	@Autowired
	private CaregiverRequestService caregiverService;
	
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	@PostMapping("/requestCare")
	public ResponseEntity<String> createCaregiverRequest(@CurrentUser UserPrincipal userPrincipal, @RequestBody CaregiverRequest caregiverRequest){
		
		caregiverService.createCaregiverRequest(userPrincipal.getId(), caregiverRequest);
		
		return ResponseEntity.ok("Request has been posted");
	}
	
	@PreAuthorize("hasRole('ROLE_VOLUNTEER')")
	@PostMapping("/takeCaregiverRequest/{request_id}")
	public ResponseEntity<String> takeCaregiverRequest(@PathVariable Long request_id,@CurrentUser UserPrincipal userPrincipal){
		
		caregiverService.takeCaregiverRequest(request_id,userPrincipal.getId());
		return ResponseEntity.ok("Thank you for giving service");
	}
	
	@PreAuthorize("hasRole('ROLE_VOLUNTEER')")
	@PostMapping("/finishedCaregiverService/{request_id}")
	public ResponseEntity<String> finishedCaregiverService(@PathVariable Long request_id){
		
		caregiverService.finishedCaregiverService(request_id);
		return null;
	}
	
	@PreAuthorize("hasRole('ROLE_VOLUNTEER')")
	@GetMapping("/retrieveTakenRequestToCargiver")
	public List<CaregiverRequest> retrieveTakenRequestToSpecificCaregiver(@CurrentUser UserPrincipal userPricipal){

		return caregiverService.retrieveTakenRequestToSpecificCaregiver(userPricipal.getId());
	}
	
	@PreAuthorize("hasRole('ROLE_VOLUNTEER')")
	@GetMapping("/retrievePendingCareRequestToCaregivers")
	public List<CaregiverRequest> retrievePendingCareRequestToCaregivers(){
		return caregiverService.retrievePendingCareRequestToCaregivers();
	}
	
}
