package com.logiclegends.MerryMeal.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Meal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String ingredient;
	private String mealDescription;
	
	@Lob
	private byte[] mealImg;

	private long price;
	
	private String status;
	
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = PartnerEntity.class)
//    @JoinColumn(name = "partner_id")
//    private PartnerEntity partner_meal;
	
	@ManyToOne(targetEntity = PartnerEntity.class)
	@JoinColumn(name = "partnerEntity_id")
	@JsonIgnore
	private PartnerEntity partnerEntity;
	
	@OneToMany(targetEntity = Order.class,mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getMealDescription() {
		return mealDescription;
	}

	public void setMealDescription(String mealDescription) {
		this.mealDescription = mealDescription;
	}

	public byte[] getMealImg() {
		return mealImg;
	}

	public void setMealImg(byte[] mealImg) {
		this.mealImg = mealImg;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


//
//	public PartnerEntity getPartner_meal() {
//		return partner_meal;
//	}
//
//	public void setPartner_meal(PartnerEntity partner_meal) {
//		this.partner_meal = partner_meal;
//	}

	public PartnerEntity getPartnerEntity() {
		return partnerEntity;
	}

	public void setPartnerEntity(PartnerEntity partnerEntity) {
		this.partnerEntity = partnerEntity;
	}
	
		
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}



	public Meal(long id, String name, String ingredient, String mealDescription, byte[] mealImg, long price,
			String status, PartnerEntity partnerEntity, List<Order> orders) {
		super();
		this.id = id;
		this.name = name;
		this.ingredient = ingredient;
		this.mealDescription = mealDescription;
		this.mealImg = mealImg;
		this.price = price;
		this.status = status;
		this.partnerEntity = partnerEntity;
		this.orders = orders;
	}

	public Meal() {
		// TODO Auto-generated constructor stub
	}

}
