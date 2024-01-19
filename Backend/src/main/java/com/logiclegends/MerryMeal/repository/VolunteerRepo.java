package com.logiclegends.MerryMeal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.MemberEntity;
import com.logiclegends.MerryMeal.entities.VolunteerEntity;

@Repository
public interface VolunteerRepo extends JpaRepository<VolunteerEntity, Long> {

	boolean existsByVolunteerEmail(String email);
	
	Optional<VolunteerEntity> findByVolunteerEmail(String email);
}
