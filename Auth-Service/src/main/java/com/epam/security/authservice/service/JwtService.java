package com.epam.security.authservice.service;


import com.epam.security.authservice.model.StringConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtService {


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public void validateToken(final String token) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"validateToken",token);
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"validateToken");
    }


    public String generateToken(String userName) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"generateToken",userName);
        Map<String, Object> claims = new HashMap<>();
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"generateToken");
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"createToken",userName);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"createToken");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"getSignKey");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"getSignKey");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
