package com.shapefit.shapefit.repository;

import com.shapefit.shapefit.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
    boolean existsByEmailAddress(String emailAddress);
    User findByEmailAddress(String emailAddress);
	User findByUsername(String userName);
}