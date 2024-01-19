package com.logiclegends.MerryMeal.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "volunteer")
public class VolunteerEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long volunteerId;

	private String volunteerName;
	
	@Email
	private String volunteerEmail;
	private String volunteerPassword;
	
	@Lob
	private byte[] evidenceImage;
	
	@Enumerated(EnumType.STRING)
	private final UserRole role = UserRole.VOLUNTEER;
	
	@OneToMany(targetEntity = Order.class, 
			cascade = CascadeType.ALL,
			mappedBy = "id",
			orphanRemoval = true)
	private List<Order> order;
	
	@OneToMany(targetEntity = CaregiverRequest.class, 
			cascade = CascadeType.ALL,
			mappedBy = "id",
			orphanRemoval = true)
	private List<CaregiverRequest> requestCaregiver;
	
	
	
	@Override
	public String toString() {
		return "VolunteerEntity [volunteerId=" + volunteerId + ", volunteerName=" + volunteerName + ", volunteerEmail="
				+ volunteerEmail + ", volunteerPassword=" + volunteerPassword + ", role=" + role + "]";
	}
	public long getVolunteerId() {
		return volunteerId;
	}
	public void setVolunteerId(long volunteerId) {
		this.volunteerId = volunteerId;
	}
	public String getVolunteerName() {
		return volunteerName;
	}
	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}
	public String getVolunteerEmail() {
		return volunteerEmail;
	}
	public void setVolunteerEmail(String volunteerEmail) {
		this.volunteerEmail = volunteerEmail;
	}
	public String getVolunteerPassword() {
		return volunteerPassword;
	}
	public void setVolunteerPassword(String volunteerPassword) {
		this.volunteerPassword = volunteerPassword;
	}
	public byte[] getEvidenceImage() {
		return evidenceImage;
	}
	public void setEvidenceImage(byte[] evidenceImage) {
		this.evidenceImage = evidenceImage;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole volunteer) {
		// TODO Auto-generated method stub
		
	}
	
	
}
