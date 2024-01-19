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
@Table(name = "partner")
public class PartnerEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long partnerId;
	private String partnerName;
	
	@Email
	private String partnerEmail;
	private String partnerPassword;
	
	private double latitude;
	private double longitude;
	
	@Lob
	private byte[] evidenceImage;

	@Enumerated(EnumType.STRING)
	private final UserRole role = UserRole.PARTNER;

//	@OneToMany(mappedBy = "partner_meal", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Meal.class, orphanRemoval = true)
//	 private List<Meal> partner_meal;

	@OneToMany(targetEntity = Meal.class, 
			cascade = CascadeType.ALL,
			mappedBy = "id",
			orphanRemoval = true)
	private List<Meal> meal;

	


	public long getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}


	public String getPartnerName() {
		return partnerName;
	}


	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public String getPartnerEmail() {
		return partnerEmail;
	}


	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}


	public String getPartnerPassword() {
		return partnerPassword;
	}


	public void setPartnerPassword(String partnerPassword) {
		this.partnerPassword = partnerPassword;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public byte[] getEvidenceImage() {
		return evidenceImage;
	}


	public void setEvidenceImage(byte[] evidenceImage) {
		this.evidenceImage = evidenceImage;
	}

//	public List<Meal> getPartner_meal() {
//		return partner_meal;
//	}
//
//
//	public void setPartner_meal(List<Meal> partner_meal) {
//		this.partner_meal = partner_meal;
//	}
	public List<Meal> getMeal() {
		return meal;
	}


	public void setMeal(List<Meal> meal) {
		this.meal = meal;
	}

	public UserRole getRole() {
		return role;
	}


	public PartnerEntity(long partnerId, String partnerName, @Email String partnerEmail, String partnerPassword,
			double latitude, double longitude, byte[] evidenceImage, List<Meal> meal) {
		super();
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.partnerEmail = partnerEmail;
		this.partnerPassword = partnerPassword;
		this.latitude = latitude;
		this.longitude = longitude;
		this.evidenceImage = evidenceImage;
//		this.partner_meal = meal;
		this.meal = meal;
	}


	public PartnerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setRole(UserRole partner) {
		// TODO Auto-generated method stub
		
	}
	
		
	

	
}
