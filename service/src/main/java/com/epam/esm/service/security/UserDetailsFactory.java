package com.epam.esm.service.security;

import com.epam.esm.common.entity.UserDetailsImpl;
import com.epam.esm.common.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserDetailsFactory {

    public static UserDetails create(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRole().getTitle()
        );
    }
}
