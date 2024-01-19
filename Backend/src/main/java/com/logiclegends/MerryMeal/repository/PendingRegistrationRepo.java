package com.logiclegends.MerryMeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.PendingRegistration;

@Repository
public interface PendingRegistrationRepo extends JpaRepository<PendingRegistration, Long>{
	
	public Boolean existsByEmail(String email);
	
//	@Query(nativeQuery = true, value = "SELECT role FROM pending_registration WHERE id = :id")
//	public UserRole getUserRole(@Param("id") long id);

//	@Query(nativeQuery = true, value = "SELECT pending_registration.role FROM pending_registration " 
//	        + "WHERE id LIKE %:keyword% OR "
//	        + "location LIKE %:keyword% OR "
//	        + "store_name LIKE %:keyword% OR "
//	        + "store_type LIKE %:keyword%")
//	public List<Stores> searchStoreByKeyword(@Param("keyword") String keyword);
}
