package com.shapefit.shapefit.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shapefit.shapefit.exception.SigninInException;
import com.shapefit.shapefit.model.dto.Signin;
import com.shapefit.shapefit.model.dto.SigninResponse;
import com.shapefit.shapefit.model.entity.SigninUser;
import com.shapefit.shapefit.model.entity.User;
import com.shapefit.shapefit.repository.SigninRepository;

@Service
public class SigninService {

    @Autowired
    private UserService userService;
   
    
    private final SigninRepository signinRepository;
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SigninService(SigninRepository signinRepository, PasswordEncoder passwordEncoder) {
        this.signinRepository = signinRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SigninResponse signin(String userName,String password) throws SigninInException {
        User signinUser1 = userService.findByUserName(userName);
        if (signinUser1 == null) {
            throw new SigninInException("Username not found");
        }
        if (!signinUser1.getPassword().equals(password)) {
            throw new SigninInException("Invalid password");
        }

        return new SigninResponse(signinUser1);
    }


}
