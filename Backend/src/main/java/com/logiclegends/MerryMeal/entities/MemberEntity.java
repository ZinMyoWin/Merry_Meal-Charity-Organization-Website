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
@Table(name = "member")
public class MemberEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long memberId;

	private String memberName;
	
	@Email
	private String memberEmail;
	private String memberPassword;
	
	@Lob
	private byte[] evidenceImage;
	 
	@Enumerated(EnumType.STRING)
	private final UserRole role = UserRole.MEMBER;
	
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

	private long orderHistory;
	
	private long caregiveRequestHistory;
	
	
	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
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

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	
	public long getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(long orderHistory) {
		this.orderHistory = orderHistory;
	}

	public List<CaregiverRequest> getRequestCaregiver() {
		return requestCaregiver;
	}

	public void setRequestCaregiver(List<CaregiverRequest> requestCaregiver) {
		this.requestCaregiver = requestCaregiver;
	}

	public long getCaregiveRequestHistory() {
		return caregiveRequestHistory;
	}

	public void setCaregiveRequestHistory(long caregiveRequestHistory) {
		this.caregiveRequestHistory = caregiveRequestHistory;
	}

	public MemberEntity(long memberId, String memberName, @Email String memberEmail, String memberPassword,
			byte[] evidenceImage, List<Order> order, long orderHistory) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberPassword = memberPassword;
		this.evidenceImage = evidenceImage;
		this.order = order;
		this.orderHistory = orderHistory;
	}

	public MemberEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setRole(UserRole member) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
