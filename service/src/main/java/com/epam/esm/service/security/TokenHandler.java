package com.epam.esm.service.security;

import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.UserDetailsImpl;
import com.epam.esm.common.exception.TokenExpiredException;
import com.epam.esm.common.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenHandler {

    private static final String CLAIM_TITLE_ID = "id";
    private static final String CLAIM_TITLE_AUTHORITIES = "authorities";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validity-time}")
    private long validityTime;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public Token generateAccessToken(Authentication authentication) {
        Date now = new Date();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Claims claims = Jwts.claims();
        claims.setSubject(userDetails.getUsername());
        claims.put(CLAIM_TITLE_ID, userDetails.getId());
        claims.put(CLAIM_TITLE_AUTHORITIES, getAuthoritiesNames(userDetails.getAuthorities()));

        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + validityTime * 1000))
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact()
        );
    }

    private List<String> getAuthoritiesNames(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (JwtException | IllegalArgumentException ex) {
            throw new TokenInvalidException();
        }
    }
}
