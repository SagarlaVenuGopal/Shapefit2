package com.shapefit.shapefit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shapefit.shapefit.model.entity.SigninUser;

@Repository
public interface SigninRepository extends JpaRepository<SigninUser, Long> {
	    SigninUser findByUsername(String username);
	    
	}


