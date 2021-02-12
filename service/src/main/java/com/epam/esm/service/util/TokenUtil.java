package com.epam.esm.service.util;

import com.epam.esm.common.entity.User;
import com.epam.esm.common.exception.TokenExpiredException;
import com.epam.esm.common.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenUtil {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validity-time}")
    private long validityTime;

    private final UserDetailsService userDetailsService;

    public TokenUtil(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String generateAccessToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(user.getLogin()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityTime * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String login = extractLogin(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String extractLogin(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Date now = new Date();
            Date expiration = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return now.before(expiration);
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (JwtException | IllegalArgumentException ex) {
            throw new TokenInvalidException();
        }
    }
}
