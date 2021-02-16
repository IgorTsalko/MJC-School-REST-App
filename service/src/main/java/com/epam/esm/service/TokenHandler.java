package com.epam.esm.service;

import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.User;
import com.epam.esm.common.exception.TokenExpiredException;
import com.epam.esm.common.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenHandler {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validity-time}")
    private long validityTime;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public Token generateAccessToken(User user) {
        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(Jwts.claims().setSubject(user.getLogin()))
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + validityTime * 1000))
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact()
        );
    }

    public String extractLogin(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (JwtException | IllegalArgumentException ex) {
            throw new TokenInvalidException();
        }
    }
}
