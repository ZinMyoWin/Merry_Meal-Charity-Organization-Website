package com.logiclegends.MerryMeal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="meal_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String day;
	private double userLatitude;
	private double userLongitude;
	private String mealType;
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Meal.class)
	@JoinColumn(name = "meal_id", insertable = false, updatable = false)
	private Meal meal;
	
	// New field to hold meal_id
    @Column(name = "meal_id")
    private Long mealId;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = MemberEntity.class)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private MemberEntity member;
	
	// New field to hold member_id
    @Column(name = "member_id")
    private Long memberId;
	
	
	@ManyToOne(targetEntity = VolunteerEntity.class)
	@JoinColumn(name = "volunteer_id")
	private VolunteerEntity volunteer;
	
	
	
	
	public Long getMealId() {
		return mealId;
	}
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	
	public Long getMemberId() {
		return memberId;
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
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public double getUserLatitude() {
		return userLatitude;
	}
	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}
	public double getUserLongitude() {
		return userLongitude;
	}
	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}
	
	
	public Meal getMeal() {
		return meal;
	}
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public MemberEntity getMember() {
		return member;
	}
	public void setMember(MemberEntity member) {
		this.member = member;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
		
	public VolunteerEntity getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(VolunteerEntity volunteer) {
		this.volunteer = volunteer;
	}
	
	
	public Order(long id, String day, double userLatitude, double userLongitude, String mealType, String status,
			Meal meal, MemberEntity member) {
		super();
		this.id = id;
		this.day = day;
		this.userLatitude = userLatitude;
		this.userLongitude = userLongitude;
		this.mealType = mealType;
		this.status = status;
		this.meal = meal;
		this.member = member;
	}
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", meal_name="+ ", day=" + day + ", userLatitude=" + userLatitude
				+ ", userLongitude=" + userLongitude + "]";
	}
	
	
	
}
