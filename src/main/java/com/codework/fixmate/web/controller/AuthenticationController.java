package com.codework.fixmate.web.controller;

import com.codework.fixmate.dao.entities.User;
import com.codework.fixmate.dao.repositories.UserRepository;
import com.codework.fixmate.security.JwtUtil;
import com.codework.fixmate.service.Authentication.authService;
import com.codework.fixmate.service.dtos.UserDTO;
import com.codework.fixmate.service.dtos.signUpRequestDTO;
import com.codework.fixmate.service.dtos.AuthenticationRequest;
import com.codework.fixmate.service.jwt.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

@RestController
public class AuthenticationController {
    @Autowired
    private authService authservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String Token_Prefix = "Bearer";
    public static final String Header_String = "Authorization";



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
        UserDTO createdUser = authservice.signUpCompany(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }


    @PostMapping({"/authenticate"})
    private void creatAuthenticationTok(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()
            ));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password" , e);
        }
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        User user = userRepository.findByEmail(authenticationRequest.getUsername());
        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role", user.getRole())
                .toString() );

        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Expose-Headers","Authorization" +
                "X-PINGOTHER ,ORIGIN ,X-Requested-With , Content-Type , Accept , X-Custom-header");

        response.addHeader(Header_String, Token_Prefix);
};








}
