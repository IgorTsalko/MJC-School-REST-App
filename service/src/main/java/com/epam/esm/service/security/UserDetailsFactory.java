package com.epam.esm.service.security;

import com.epam.esm.common.entity.UserDetailsImpl;
import com.epam.esm.common.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class UserDetailsFactory {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String CLAIM_TITLE_ID = "id";
    private static final String CLAIM_TITLE_AUTHORITIES = "authorities";

    public static UserDetails create(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getTitle()))
        );
    }

    @SuppressWarnings("unchecked")
    public static UserDetails create(Claims claims) {
        Long id = claims.get(CLAIM_TITLE_ID, Long.class);
        String login = claims.getSubject();
        Collection<?> authorities = claims.get(CLAIM_TITLE_AUTHORITIES, Collection.class);
        if (authorities != null) {
            authorities = authorities
                    .stream()
                    .map(a -> new SimpleGrantedAuthority((String) a))
                    .collect(Collectors.toList());
        }

        return new UserDetailsImpl(
                id,
                login,
                null,
                (Collection<? extends GrantedAuthority>) authorities
        );
    }
}
