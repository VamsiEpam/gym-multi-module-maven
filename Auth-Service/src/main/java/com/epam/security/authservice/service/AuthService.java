package com.epam.security.authservice.service;


import com.epam.security.authservice.model.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private JwtService jwtService;


    public String generateToken(String username) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"generateToken",username);
        String token = jwtService.generateToken(username);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"generateToken");
        return token;
    }

    public void validateToken(String token) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"validateToken",token);
        jwtService.validateToken(token);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"validateToken");
    }


}
