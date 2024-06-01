package com.shapefit.shapefit.model.dto;

import com.shapefit.shapefit.model.entity.SigninUser;
import com.shapefit.shapefit.model.entity.User;

public class SigninResponse {
    private String message;
    private String username;

    public SigninResponse(User signinUser1) {
        this.username = signinUser1.getUsername();
        this.message = "Login successful";
    }

    public SigninResponse(String message) {
        this.message = message;
        this.username = null; 
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
