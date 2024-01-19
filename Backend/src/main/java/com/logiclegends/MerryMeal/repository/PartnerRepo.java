package com.logiclegends.MerryMeal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.MemberEntity;
import com.logiclegends.MerryMeal.entities.PartnerEntity;
@Repository
public interface PartnerRepo extends JpaRepository<PartnerEntity, Long> {

	boolean existsByPartnerEmail(String email);
	
	Optional<PartnerEntity> findByPartnerEmail(String email);
}
