package com.epam.esm.server.security;

import com.epam.esm.service.security.TokenHandler;
import com.epam.esm.service.security.UserDetailsFactory;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler {

    private final TokenHandler tokenHandler;

    public AuthenticationHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = tokenHandler.extractClaims(token);
        UserDetails userDetails = UserDetailsFactory.create(claims);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
