package com.logiclegends.MerryMeal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class CaregiverRequest {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private String dayNeed;
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = MemberEntity.class)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private MemberEntity member;

	
	@ManyToOne(targetEntity = VolunteerEntity.class)
	@JoinColumn(name = "volunteer_id")
	private VolunteerEntity volunteer;
	
	// New field to hold member_id
    @Column(name = "member_id")
    private Long memberId;
    
    
	@Transient
    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDayNeed() {
		return dayNeed;
	}
	public void setDayNeed(String dayNeed) {
		this.dayNeed = dayNeed;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
//	@Transient
	public MemberEntity getMember() {
		return member;
	}
	public void setMember(MemberEntity member) {
		this.member = member;
	}
	public VolunteerEntity getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(VolunteerEntity volunteer) {
		this.volunteer = volunteer;
	}
	
	public CaregiverRequest(long id, String dayNeed, String status, MemberEntity member, VolunteerEntity volunteer) {
		super();
		this.id = id;
		this.dayNeed = dayNeed;
		this.status = status;
		this.member = member;
		this.volunteer = volunteer;
	}
	public CaregiverRequest(long id, String dayNeed) {
		super();
		this.id = id;
		this.dayNeed = dayNeed;
	}
	public CaregiverRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
}
