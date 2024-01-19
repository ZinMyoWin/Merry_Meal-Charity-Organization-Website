package com.logiclegends.MerryMeal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.AdminEntity;

@Repository
public interface AdminRepo  extends JpaRepository<AdminEntity, Long> {
	
	boolean existsByAdminEmail(String email);
	
	Optional<AdminEntity> findByAdminEmail(String email);

}
