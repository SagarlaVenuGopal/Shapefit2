package com.shapefit.shapefit.controller;

import com.shapefit.shapefit.exception.PasswordMismatchException;
import com.shapefit.shapefit.exception.UserAlreadyExistsException;
import com.shapefit.shapefit.model.dto.SignupRequest;
import com.shapefit.shapefit.model.dto.SignupResponse;
import com.shapefit.shapefit.model.dto.VerifyOtpRequest;
import com.shapefit.shapefit.model.entity.User;
import com.shapefit.shapefit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<SignupResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        SignupResponse response = null;
        try {
            response = userService.registerUser(signupRequest);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new SignupResponse(e.getMessage()));
        } catch (PasswordMismatchException e) {
            return ResponseEntity.badRequest().body(new SignupResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SignupResponse("An error occurred. Please try again."));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<SignupResponse> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        SignupResponse response = userService.verifyOTP(verifyOtpRequest.getEmailAddress(), verifyOtpRequest.getOtp());
        return ResponseEntity.ok(response);
    }
}