package com.logiclegends.MerryMeal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.logiclegends.MerryMeal.service.EmailService;
import com.logiclegends.MerryMeal.service.RegistrationServiceImpl;

class RegistrationServiceImplTest {

	 @Mock
	    private PendingRegistrationRepo pendingRegRepo;

	    @Mock
	    private MemberRepo memberRepo;

	    @Mock
	    private VolunteerRepo volunteerRepo;

	    @Mock
	    private PartnerRepo partnerRepo;

	    @Mock
	    private AdminRepo adminRepo;

	    @Mock
	    private MultipartFile file;

	    @Mock
	    private EmailService emailService;

	    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        registrationService = new RegistrationServiceImpl();
        registrationService.pendingRegRepo = pendingRegRepo;
        registrationService.memberRepo = memberRepo;
        registrationService.volunteerRepo = volunteerRepo;
        registrationService.partnerRepo = partnerRepo;
        registrationService.adminRepo = adminRepo;
        registrationService.emailService = emailService;
    }

    @Test
    void approveRegistration_Member_Success() {
        Long registrationId = 1L;
        UserRole role = UserRole.MEMBER;
        PendingRegistration pendingRegistration = new PendingRegistration();
        pendingRegistration.setName("Test User");
        pendingRegistration.setEmail("test@example.com");
        pendingRegistration.setPassword("password");
        pendingRegistration.setRole(UserRole.MEMBER);

        when(pendingRegRepo.findById(registrationId)).thenReturn(Optional.of(pendingRegistration));
        when(memberRepo.save(any())).thenReturn(new MemberEntity());

        assertDoesNotThrow(() -> registrationService.approveRegistration(registrationId, role));
    }

    @Test
    void approveRegistration_Volunteer_Success() {
        Long registrationId = 1L;
        UserRole role = UserRole.VOLUNTEER;
        PendingRegistration pendingRegistration = new PendingRegistration();
        pendingRegistration.setName("Test Volunteer");
        pendingRegistration.setEmail("volunteer@example.com");
        pendingRegistration.setPassword("password");
        pendingRegistration.setRole(UserRole.VOLUNTEER);

        when(pendingRegRepo.findById(registrationId)).thenReturn(Optional.of(pendingRegistration));
        when(volunteerRepo.save(any())).thenReturn(new VolunteerEntity());

        assertDoesNotThrow(() -> registrationService.approveRegistration(registrationId, role));
    }

    @Test
    void approveRegistration_Partner_Success() {
        Long registrationId = 1L;
        UserRole role = UserRole.PARTNER;
        PendingRegistration pendingRegistration = new PendingRegistration();
        pendingRegistration.setName("Test Partner");
        pendingRegistration.setEmail("partner@example.com");
        pendingRegistration.setPassword("password");
        pendingRegistration.setRole(UserRole.PARTNER);

        when(pendingRegRepo.findById(registrationId)).thenReturn(Optional.of(pendingRegistration));
        when(partnerRepo.save(any())).thenReturn(new PartnerEntity());

        assertDoesNotThrow(() -> registrationService.approveRegistration(registrationId, role));
    }

    @Test
    void approveRegistration_InvalidRole_ThrowsException() {
        Long registrationId = 1L;
        UserRole role = UserRole.ADMIN; // Invalid role

        when(pendingRegRepo.findById(registrationId)).thenReturn(Optional.of(new PendingRegistration()));

        assertThrows(IllegalArgumentException.class, () -> registrationService.approveRegistration(registrationId, role));
    }

    @Test
    void rejectRegistration_Success() {
        Long registrationId = 1L;
        PendingRegistration pendingRegistration = new PendingRegistration();
        pendingRegistration.setEmail("test@example.com");

        when(pendingRegRepo.findById(registrationId)).thenReturn(Optional.of(pendingRegistration));

        assertDoesNotThrow(() -> registrationService.rejectRegistration(registrationId));
    }

    @Test
    void loadUserByUsername_Admin_Success() {
        String email = "admin@example.com";
        AdminEntity admin = new AdminEntity();
        admin.setAdminId(1L);
        admin.setAdminEmail(email);
        admin.setAdminPassword("password");
        admin.setRole(UserRole.ADMIN);

        when(adminRepo.existsByAdminEmail(email)).thenReturn(true);
        when(adminRepo.findByAdminEmail(email)).thenReturn(Optional.of(admin));

        assertDoesNotThrow(() -> registrationService.loadUserByUsername(email));
    }

    @Test
    void loadUserByUsername_Member_Success() {
        String email = "member@example.com";
        MemberEntity member = new MemberEntity();
        member.setMemberId(1L);
        member.setMemberEmail(email);
        member.setMemberPassword("password");
        member.setRole(UserRole.MEMBER);

        when(adminRepo.existsByAdminEmail(email)).thenReturn(false);
        when(memberRepo.existsByMemberEmail(email)).thenReturn(true);
        when(memberRepo.findByMemberEmail(email)).thenReturn(Optional.of(member));

        assertDoesNotThrow(() -> registrationService.loadUserByUsername(email));
    }

    @Test
    void loadUserByUsername_Partner_Success() {
        String email = "partner@example.com";
        PartnerEntity partner = new PartnerEntity();
        partner.setPartnerId(1L);
        partner.setPartnerEmail(email);
        partner.setPartnerPassword("password");
        partner.setRole(UserRole.PARTNER);

        when(adminRepo.existsByAdminEmail(email)).thenReturn(false);
        when(memberRepo.existsByMemberEmail(email)).thenReturn(false);
        when(partnerRepo.existsByPartnerEmail(email)).thenReturn(true);
        when(partnerRepo.findByPartnerEmail(email)).thenReturn(Optional.of(partner));

        assertDoesNotThrow(() -> registrationService.loadUserByUsername(email));
    }

    @Test
    void loadUserByUsername_Volunteer_Success() {
        String email = "volunteer@example.com";
        VolunteerEntity volunteer = new VolunteerEntity();
        volunteer.setVolunteerId(1L);
        volunteer.setVolunteerEmail(email);
        volunteer.setVolunteerPassword("password");
        volunteer.setRole(UserRole.VOLUNTEER);

        when(adminRepo.existsByAdminEmail(email)).thenReturn(false);
        when(memberRepo.existsByMemberEmail(email)).thenReturn(false);
        when(partnerRepo.existsByPartnerEmail(email)).thenReturn(false);
        when(volunteerRepo.existsByVolunteerEmail(email)).thenReturn(true);
        when(volunteerRepo.findByVolunteerEmail(email)).thenReturn(Optional.of(volunteer));

        assertDoesNotThrow(() -> registrationService.loadUserByUsername(email));
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        String email = "nonexistent@example.com";

        when(adminRepo.existsByAdminEmail(email)).thenReturn(false);
        when(memberRepo.existsByMemberEmail(email)).thenReturn(false);
        when(partnerRepo.existsByPartnerEmail(email)).thenReturn(false);
        when(volunteerRepo.existsByVolunteerEmail(email)).thenReturn(false);

        assertThrows(UsernameNotFoundException.class, () -> registrationService.loadUserByUsername(email));
    }

    @Test
    void savePendingMember_Success() throws IOException {
        PendingRegistration pending = new PendingRegistration();
        pending.setEmail("test@example.com");
        when(pendingRegRepo.existsByEmail(any())).thenReturn(false);
        when(file.isEmpty()).thenReturn(false);

        assertDoesNotThrow(() -> registrationService.savePendingMember(pending, file));
    }

    @Test
    void savePendingMember_DuplicateEmail() throws IOException {
        PendingRegistration pending = new PendingRegistration();
        pending.setEmail("test@example.com");
        when(pendingRegRepo.existsByEmail(any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> registrationService.savePendingMember(pending, file));
    }

    @Test
    void savePendingVolunteer_Success() throws IOException {
        PendingRegistration pending = new PendingRegistration();
        pending.setEmail("test@example.com");
        when(pendingRegRepo.existsByEmail(any())).thenReturn(false);
        when(file.isEmpty()).thenReturn(false);

        assertDoesNotThrow(() -> registrationService.savePendingVolunteer(pending, file));
    }

    @Test
    void savePendingVolunteer_DuplicateEmail() throws IOException {
        PendingRegistration pending = new PendingRegistration();
        pending.setEmail("test@example.com");
        when(pendingRegRepo.existsByEmail(any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> registrationService.savePendingVolunteer(pending, file));
    }

    // Add more test cases for other methods as needed
}

