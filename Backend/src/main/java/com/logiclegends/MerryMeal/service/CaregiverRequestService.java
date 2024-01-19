package com.logiclegends.MerryMeal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiclegends.MerryMeal.entities.CaregiverRequest;
import com.logiclegends.MerryMeal.entities.MemberEntity;
import com.logiclegends.MerryMeal.entities.VolunteerEntity;
import com.logiclegends.MerryMeal.repository.CaregiverRepo;
import com.logiclegends.MerryMeal.repository.MemberRepo;
import com.logiclegends.MerryMeal.repository.VolunteerRepo;

@Service
public class CaregiverRequestService {

	@Autowired
	private CaregiverRepo caregiverRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private VolunteerRepo volunteerRepo;
	
	
	public CaregiverRequest createCaregiverRequest(Long member_id,CaregiverRequest caregiverRequest ) {
		MemberEntity memberEntity = memberRepo.findById(member_id)
				.orElseThrow(() -> new RuntimeException("Member not found"));
	
		caregiverRequest.setDayNeed(caregiverRequest.getDayNeed());
		caregiverRequest.setMemberId(memberEntity.getMemberId());
			
		 caregiverRequest.setMember(memberEntity);
		caregiverRequest.setStatus("Pending");
		memberEntity.setCaregiveRequestHistory(memberEntity.getCaregiveRequestHistory()+1);
		
		return caregiverRepo.save(caregiverRequest);
		
	}
	
	public CaregiverRequest takeCaregiverRequest(Long caregiver_request_id, Long volunteer_id) {
		CaregiverRequest caregiverRequest = caregiverRepo.findById(caregiver_request_id)
				.orElseThrow(() -> new RuntimeException("Caregiver Request not found"));
		
		VolunteerEntity volunteerEntity = volunteerRepo.findById(volunteer_id)
				.orElseThrow(() -> new RuntimeException("Volunteer not found"));
		
		caregiverRequest.setStatus("In progress");
		
		caregiverRequest.setVolunteer(volunteerEntity);
		
		
		
		return caregiverRepo.save(caregiverRequest);
		
	}
	
	public CaregiverRequest finishedCaregiverService(Long caregiver_request_id) {
		CaregiverRequest caregiverRequest = caregiverRepo.findById(caregiver_request_id)
				.orElseThrow(() -> new RuntimeException("Caregiver Request not found"));
		
		caregiverRequest.setStatus("Finished");
		caregiverRepo.save(caregiverRequest);
		/* caregiverRepo.deleteById(caregiverRequest.getId()); */
		return null;		
	}
	
	public List<CaregiverRequest> retrieveTakenRequestToSpecificCaregiver(Long volunteer_id) {
		List<CaregiverRequest> caregiverRequest = caregiverRepo.findByVolunteerId(volunteer_id);
		
		return caregiverRequest;
	}
	
	public List<CaregiverRequest> retrievePendingCareRequestToCaregivers(){
		return caregiverRepo.findByStatus("Pending");
	}
	
	
}
