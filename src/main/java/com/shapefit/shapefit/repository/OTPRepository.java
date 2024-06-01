package com.shapefit.shapefit.repository;

import com.shapefit.shapefit.model.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    OTP findByEmailAddressAndOtp(String emailAddress, String otp);
}