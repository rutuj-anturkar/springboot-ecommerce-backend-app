package com.ecommerce.controller;

import com.ecommerce.dto.UserLoginRequestDTO;
import com.ecommerce.dto.UserRegistrationDTO;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(UserRegistrationDTO registrationDTO){
        userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(UserLoginRequestDTO loginRequestDTO){
        return  ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginRequestDTO));
    }
}
