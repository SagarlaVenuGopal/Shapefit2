package com.shapefit.shapefit.service;

import com.shapefit.shapefit.exception.PasswordMismatchException;
import com.shapefit.shapefit.exception.UserAlreadyExistsException;
import com.shapefit.shapefit.model.dto.SignupRequest;
import com.shapefit.shapefit.model.dto.SignupResponse;
import com.shapefit.shapefit.model.entity.OTP;
import com.shapefit.shapefit.model.entity.User;
import com.shapefit.shapefit.repository.OTPRepository;
import com.shapefit.shapefit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public SignupResponse registerUser(SignupRequest signupRequest) throws UserAlreadyExistsException, PasswordMismatchException {
        if (userRepository.existsByEmailAddress(signupRequest.getEmailAddress())) {
            throw new UserAlreadyExistsException("User with this email address already exists");
        }
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmailAddress(signupRequest.getEmailAddress());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(signupRequest.getRole());
        user.setVerified(false);
        userRepository.save(user);

        // Generate and send OTP
        generateAndSendOTP(user.getEmailAddress());

        return new SignupResponse("User registered successfully. Please verify your email.");
    }

    private void generateAndSendOTP(String emailAddress) {
        String otp = String.valueOf(new Random().nextInt(999999));
        OTP otpEntity = new OTP();
        otpEntity.setEmailAddress(emailAddress);
        otpEntity.setOtp(otp);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(10));
        otpRepository.save(otpEntity);
        // Send OTP email
        emailService.sendEmail(emailAddress, "Your OTP Code", "Your OTP code is: " + otp);
    }

    public SignupResponse verifyOTP(String emailAddress, String otp) {
        OTP otpEntity = otpRepository.findByEmailAddressAndOtp(emailAddress, otp);
        if (otpEntity == null || otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setVerified(true);
        userRepository.save(user);
        otpRepository.delete(otpEntity);

        return new SignupResponse("Email verified successfully");
    }
    public User findByUserName(String userName) {
    	return userRepository.findByUsername(userName);
    }
}