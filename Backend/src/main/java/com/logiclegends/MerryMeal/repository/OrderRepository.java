package com.logiclegends.MerryMeal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.Meal;
import com.logiclegends.MerryMeal.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

	public List<Order> findByStatus(String status);
	
	@Query("SELECT o FROM Order o WHERE o.volunteer.id = :volunteerId")
	List<Order> findByVolunteerId(@Param("volunteerId") Long volunteerId);


}
