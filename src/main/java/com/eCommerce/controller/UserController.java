package com.eCommerce.controller;

import com.eCommerce.entity.AuthenticationRequest;
import com.eCommerce.entity.AuthenticationResponse;
import com.eCommerce.security.JwtUtil;
import com.eCommerce.security.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {
   // @Autowired
    //private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        String message = "";
        System.out.println("On Controller");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if(StringUtils.endsWithIgnoreCase(userDetails.getUsername(), authenticationRequest.getUsername())){
            message = "Password Invalid";
            if(passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())){
                final String jwt = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            }
        }else {
            message = "User Not Found";
        }
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);



    }

    public boolean authenticate(String username, String password) {
        System.out.println("Inside of authenticate with userName = "+username +" password = {}"+password);

        return true;
    }
}
