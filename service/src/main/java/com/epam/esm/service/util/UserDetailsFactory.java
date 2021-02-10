package com.epam.esm.service.util;

import com.epam.esm.common.entity.JwtUser;
import com.epam.esm.common.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserDetailsFactory {

    public static UserDetails create(User user) {
        return new JwtUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getRole().getTitle()
        );
    }
}
