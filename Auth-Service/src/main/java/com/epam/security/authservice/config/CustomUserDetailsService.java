package com.epam.security.authservice.config;


import com.epam.security.authservice.model.StringConstants;
import com.epam.security.authservice.model.UserCredential;
import com.epam.security.authservice.repository.UserCredentialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"loadUserByUsername",username);
        Optional<UserCredential> credential = repository.findByUserName(username);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"loadUserByUsername");
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }
}
