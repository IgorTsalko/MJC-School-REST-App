package com.epam.esm.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler {

    private final UserDetailsService userDetailsService;
    private final TokenHandler tokenHandler;

    public AuthenticationHandler(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                 TokenHandler tokenHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenHandler = tokenHandler;
    }

    public Authentication getAuthentication(String token) {
        String login = tokenHandler.extractLogin(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
