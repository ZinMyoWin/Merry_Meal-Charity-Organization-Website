package com.logiclegends.MerryMeal.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class PendingRegistration {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String name;
    private String email;
    private String password;
    
    @Lob
    private byte[] evidenceImage;
    
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	private double latitude;
	private double longitude;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public void setRole(UserRole role) {
		this.role = role;
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
	public PendingRegistration(Long id, String name, String email, String password, byte[] evidenceImage, UserRole role,
			double latitude, double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.evidenceImage = evidenceImage;
		this.role = role;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public PendingRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}
