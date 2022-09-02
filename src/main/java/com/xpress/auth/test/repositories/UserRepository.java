package com.xpress.auth.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xpress.auth.test.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	@Query("SELECT us FROM user us WHERE us.email = ?1")
	UserEntity findByEmail(String email);
	
	@Query("SELECT us FROM user us WHERE us.userId = ?1")
	UserEntity findByUserId(String userId);
}
