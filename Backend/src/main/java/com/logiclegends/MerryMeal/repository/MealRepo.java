package com.logiclegends.MerryMeal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.Meal;

@Repository
public interface MealRepo extends JpaRepository<Meal, Long> {

	
	public List<Meal> findByStatus(String status);
	
	
	
//	@Query(nativeQuery = true, value = "SELECT * FROM meal WHERE status = 'pending';")
//	public List<Meal> searchMealByPending();
	
//	@Query("SELECT * FROM Meal WHERE status = 'pending'")
//	public List<Meal> searchMealByPending();
	
	@Query(nativeQuery = true, value = "SELECT * FROM meal WHERE meal.status = 'pending'")
	public List<Meal> searchMealByPending();
	
	@Query(nativeQuery = true, value = "SELECT * FROM meal WHERE status = 'approved';")
	public List<Meal> searchMealByApproved();


	
}
