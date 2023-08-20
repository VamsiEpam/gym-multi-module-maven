package com.epam.security.authservice.controller;


import com.epam.security.authservice.customexception.InvalidTokenException;
import com.epam.security.authservice.dto.AuthRequest;
import com.epam.security.authservice.model.StringConstants;
import com.epam.security.authservice.model.UserCredential;
import com.epam.security.authservice.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    public static final String GET_TOKEN = "getToken";
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequest authRequest) {
        log.info(StringConstants.START_CONTROLLER_METHOD.getValue(), GET_TOKEN,this.getClass().getName(),authRequest);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            log.info(StringConstants.EXIT_CONTROLLER_METHOD.getValue(), GET_TOKEN);
            return service.generateToken(authRequest.getUsername());
        } else {

            log.info(StringConstants.ERROR_MESSAGE.getValue(), GET_TOKEN,this.getClass().getName());
            throw new InvalidTokenException("invalid access");
        }
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token) {
        log.info(StringConstants.START_CONTROLLER_METHOD.getValue(),"validateToken",this.getClass().getName(),token);
        service.validateToken(token);
        log.info(StringConstants.EXIT_CONTROLLER_METHOD.getValue(),"validateToken");
        return true;
    }
}
