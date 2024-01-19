package com.logiclegends.MerryMeal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.CaregiverRequest;


@Repository
public interface CaregiverRepo extends JpaRepository<CaregiverRequest, Long>{

	public List<CaregiverRequest> findByStatus(String status);
	
	@Query("SELECT o FROM CaregiverRequest o WHERE o.volunteer.id = :volunteerId")
	List<CaregiverRequest> findByVolunteerId(@Param("volunteerId") Long volunteerId);

}
