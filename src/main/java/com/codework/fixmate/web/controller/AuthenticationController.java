package com.codework.fixmate.web.controller;

import com.codework.fixmate.service.Authentication.authService;
import com.codework.fixmate.service.dtos.UserDTO;
import com.codework.fixmate.service.dtos.signUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private authService authservice;

    @PostMapping("/client/sign_up")
    public ResponseEntity<?> signUpClient (@RequestBody signUpRequestDTO signupRequestDTO){
        if(authservice.presentByEmail(signupRequestDTO .getEmail())){
            return new ResponseEntity<>("Client with this email already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO createdUser = authservice.signUpClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }

    @PostMapping("/company/sign_up")
    public ResponseEntity<?> signUpCompany (@RequestBody signUpRequestDTO signupRequestDTO){
        if(authservice.presentByEmail(signupRequestDTO .getEmail())){
            return new ResponseEntity<>("Company with this email already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO createdUser = authservice.signUpClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }






}
