package com.logiclegends.MerryMeal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logiclegends.MerryMeal.entities.MemberEntity;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
	
	boolean existsByMemberEmail(String email);
	
	Optional<MemberEntity> findByMemberEmail(String email);

}
