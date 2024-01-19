package com.logiclegends.MerryMeal.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "admin")
public class AdminEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long adminId;

	private String adminName;
	
	@Email
	private String adminEmail;
	private String adminPassword;
	@Enumerated(EnumType.STRING)
	private final UserRole role = UserRole.ADMIN;
	@Override
	public String toString() {
		return "AdminEntity [adminId=" + adminId + ", adminName=" + adminName + ", adminEmail=" + adminEmail
				+ ", adminPassword=" + adminPassword + ", role=" + role + "]";
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole admin) {
		// TODO Auto-generated method stub
		
	}
	
	
}
