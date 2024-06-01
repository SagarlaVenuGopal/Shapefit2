package com.shapefit.shapefit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shapefit.shapefit.exception.SigninInException;
import com.shapefit.shapefit.model.dto.Signin;
import com.shapefit.shapefit.model.dto.SigninResponse;
import com.shapefit.shapefit.model.entity.SigninUser;
import com.shapefit.shapefit.service.SigninService;

@RestController
@RequestMapping("/api")
public class SigninController {

    private final SigninService signinService;

    @Autowired
    public SigninController(SigninService signinService) {
        this.signinService = signinService;
    }
    @GetMapping("/signin")
    public ResponseEntity<SigninResponse> login(@RequestParam String userName,@RequestParam String password) {
        try {
            SigninResponse response = signinService.signin(userName,password);
            return ResponseEntity.ok(response);
        } catch (SigninInException e) {
            SigninResponse errorResponse = new SigninResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
