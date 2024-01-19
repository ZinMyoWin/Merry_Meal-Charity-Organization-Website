package com.logiclegends.MerryMeal.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.logiclegends.MerryMeal.entities.AdminEntity;
import com.logiclegends.MerryMeal.entities.MemberEntity;
import com.logiclegends.MerryMeal.entities.PartnerEntity;
import com.logiclegends.MerryMeal.entities.PendingRegistration;
import com.logiclegends.MerryMeal.entities.UserRole;
import com.logiclegends.MerryMeal.entities.VolunteerEntity;
import com.logiclegends.MerryMeal.exception.BadRequestException;
import com.logiclegends.MerryMeal.repository.AdminRepo;
import com.logiclegends.MerryMeal.repository.MemberRepo;
import com.logiclegends.MerryMeal.repository.PartnerRepo;
import com.logiclegends.MerryMeal.repository.PendingRegistrationRepo;
import com.logiclegends.MerryMeal.repository.VolunteerRepo;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService, UserDetailsService{

	
	@Autowired
	public PendingRegistrationRepo pendingRegRepo;
	
	@Autowired
	public MemberRepo memberRepo;

    @Autowired
    public VolunteerRepo volunteerRepo;

    @Autowired
    public PartnerRepo partnerRepo;
    
    @Autowired
    public AdminRepo adminRepo;
    
    @Autowired
    public EmailService emailService;
    
    //save member in pending table
	@Override
	public void savePendingMember(PendingRegistration pending, MultipartFile file) throws IOException {
		
		if(pendingRegRepo.existsByEmail(pending.getEmail()) 
				|| memberRepo.existsByMemberEmail(pending.getEmail())
				|| volunteerRepo.existsByVolunteerEmail(pending.getEmail()) 
				|| partnerRepo.existsByPartnerEmail(pending.getEmail())
				|| adminRepo.existsByAdminEmail(pending.getEmail())) {
			throw new BadRequestException("Duplicate Email");
		}
		
		// save image
		if (!file.isEmpty()) {
            pending.setEvidenceImage(file.getBytes());
        }
		//set role
		pending.setRole(UserRole.MEMBER);
		pendingRegRepo.save(pending);
	}
	
	//save Volunteer in pending table
	@Override
	public void savePendingVolunteer(PendingRegistration pending, MultipartFile file) throws IOException {
		if(pendingRegRepo.existsByEmail(pending.getEmail()) 
				|| memberRepo.existsByMemberEmail(pending.getEmail())
				|| volunteerRepo.existsByVolunteerEmail(pending.getEmail()) 
				|| partnerRepo.existsByPartnerEmail(pending.getEmail())
				|| adminRepo.existsByAdminEmail(pending.getEmail())) {
			throw new BadRequestException("Duplicate Email");
		}
		// save image
				if (!file.isEmpty()) {
		            pending.setEvidenceImage(file.getBytes());
		        }
				
				
		pending.setRole(UserRole.VOLUNTEER);
		pendingRegRepo.save(pending);
		
		
	}
	//save Partner in pending table
	@Override
	public void savePendingPartner(PendingRegistration pending, MultipartFile file) throws IOException {
		if(pendingRegRepo.existsByEmail(pending.getEmail()) 
				|| memberRepo.existsByMemberEmail(pending.getEmail())
				|| volunteerRepo.existsByVolunteerEmail(pending.getEmail()) 
				|| partnerRepo.existsByPartnerEmail(pending.getEmail())
				|| adminRepo.existsByAdminEmail(pending.getEmail())) {
			throw new BadRequestException("Duplicate Email");
		}
		// save image
		if (!file.isEmpty()) {
            pending.setEvidenceImage(file.getBytes());
        }		
		
		// TODO Auto-generated method stub
		pending.setRole(UserRole.PARTNER);
		
		pendingRegRepo.save(pending);
		
	}
	
	//Register admin
	@Override
	public void registerAdmin(AdminEntity admin) {		
		if(pendingRegRepo.existsByEmail(admin.getAdminEmail()) 
				|| memberRepo.existsByMemberEmail(admin.getAdminEmail()) 
				|| volunteerRepo.existsByVolunteerEmail(admin.getAdminEmail()) 
				|| partnerRepo.existsByPartnerEmail(admin.getAdminEmail()) 
				|| adminRepo.existsByAdminEmail(admin.getAdminEmail())) {
			throw new BadRequestException("Duplicate Email");
		}
		adminRepo.save(admin);		
	}

	@Override
	public List<PendingRegistration> retrievePendingUsers() {		
		return pendingRegRepo.findAll();
	}
	
	
	
	public UserRole getRoleById(long id) {
		return pendingRegRepo.findById(id).get().getRole();
	}
	
    
    //save in respective table when approved
    public void approveRegistration(Long registrationId, UserRole role) {
        PendingRegistration pendingRegistration = pendingRegRepo.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Pending registration not found"));
        
        String subject = "Approval Confirmation";
        String text = "Your registration request has been approved. You can now log in.";
        
        // Depending on the user type, move data to the respective table
        switch (role) {
            case MEMBER:
                MemberEntity member = new MemberEntity();
                // Map data from pendingRegistration to member
                member.setMemberName(pendingRegistration.getName());
                member.setMemberEmail(pendingRegistration.getEmail());
                member.setMemberPassword(pendingRegistration.getPassword());
                member.setEvidenceImage(pendingRegistration.getEvidenceImage());
                
                // Save member to MemberRepository
                memberRepo.save(member);
                
                // Send approval email to the user               
                emailService.sendEmail(member.getMemberEmail(), subject, text);
                
                break;
                
            case VOLUNTEER:
                VolunteerEntity volunteer = new VolunteerEntity();
                // Map data from pendingRegistration to volunteer
                volunteer.setVolunteerName(pendingRegistration.getName());
                volunteer.setVolunteerEmail(pendingRegistration.getEmail());
                volunteer.setVolunteerPassword(pendingRegistration.getPassword());
                volunteer.setEvidenceImage(pendingRegistration.getEvidenceImage());
                
                // Save volunteer to VolunteerRepository
                volunteerRepo.save(volunteer);
                
             // Send approval email to the user               
                emailService.sendEmail(volunteer.getVolunteerEmail(), subject, text);
                break;
                
            case PARTNER:
                PartnerEntity partner = new PartnerEntity();
                // Map data from pendingRegistration to partner
                partner.setPartnerName(pendingRegistration.getName());
                partner.setPartnerEmail(pendingRegistration.getEmail());
                partner.setPartnerPassword(pendingRegistration.getPassword());
                partner.setEvidenceImage(pendingRegistration.getEvidenceImage());
                
                partner.setLatitude(pendingRegistration.getLatitude());
                partner.setLongitude(pendingRegistration.getLongitude());
                
                // Save partner to PartnerRepository
                partnerRepo.save(partner);
                
             // Send approval email to the user               
                emailService.sendEmail(partner.getPartnerEmail(), subject, text);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type");
        }

        // Update pending registration status to approved
        pendingRegRepo.delete(pendingRegistration);

    }
    
    

    //reject in respective table when approved
    @Override
    public void rejectRegistration(Long registrationId) {           
    	 PendingRegistration pendingRegistration = pendingRegRepo.findById(registrationId)
                 .orElseThrow(() -> new EntityNotFoundException("Pending registration not found"));
           
        String subject = "Registration Rejection";
        String text = "Unfortunately, your registration request has been rejected.";
        emailService.sendEmail(pendingRegistration.getEmail(), subject, text);

        pendingRegRepo.deleteById(registrationId);
    }

        
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    	
//        MemberEntity member = memberRepo.findByMemberEmail(email)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));
//        
//        UserPrincipal userPrincipal = new UserPrincipal(member.getMemberEmail(),member.getMemberPassword(), authorities, member.getRole());
//        return userPrincipal;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
    	UserPrincipal userPrincipal = null;
    	
    	
    	if (adminRepo.existsByAdminEmail(email)) {
    		AdminEntity admin = adminRepo.findByAdminEmail(email).get();    	            
    	        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    	       userPrincipal = new UserPrincipal(admin.getAdminId(),admin.getAdminEmail(),admin.getAdminPassword(), authorities, admin.getRole());
    	}    	
    	else if (memberRepo.existsByMemberEmail(email)) {
            MemberEntity member = memberRepo.findByMemberEmail(email).get(); 
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));
            userPrincipal = new UserPrincipal(member.getMemberId(),member.getMemberEmail(), member.getMemberPassword(), authorities, member.getRole());
        } 
    	else if (partnerRepo.existsByPartnerEmail(email)) {
            PartnerEntity partner = partnerRepo.findByPartnerEmail(email).get(); 
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_PARTNER"));
            userPrincipal = new UserPrincipal(partner.getPartnerId(),partner.getPartnerEmail(), partner.getPartnerPassword(), authorities, partner.getRole());
        } 
    	else if (volunteerRepo.existsByVolunteerEmail(email)) {
            VolunteerEntity volunteer = volunteerRepo.findByVolunteerEmail(email).get(); 
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_VOLUNTEER"));
            userPrincipal = new UserPrincipal(volunteer.getVolunteerId(),volunteer.getVolunteerEmail(), volunteer.getVolunteerPassword(), authorities, volunteer.getRole());
        }
    	else{
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        return userPrincipal;
    }

	
}
