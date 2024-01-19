package com.logiclegends.MerryMeal.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.logiclegends.MerryMeal.entities.AdminEntity;
import com.logiclegends.MerryMeal.entities.PendingRegistration;
import com.logiclegends.MerryMeal.entities.UserRole;

@Service
public interface RegistrationService {
	
	//Member
	public void savePendingMember(PendingRegistration pending, MultipartFile file) throws IOException;
	public void savePendingVolunteer(PendingRegistration pending, MultipartFile file) throws IOException;
	public void savePendingPartner(PendingRegistration pending, MultipartFile file) throws IOException;
	
	public void registerAdmin(AdminEntity admin);
	
	public List<PendingRegistration> retrievePendingUsers();
	
	public void approveRegistration(Long registrationId, UserRole role);
	
	public void rejectRegistration(Long registrationId);

	

}
