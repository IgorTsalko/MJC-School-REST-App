package com.epam.esm.server.security;

import com.epam.esm.common.entity.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    public boolean isOwnData(Authentication authentication, Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("userDetails.getId(): " + userDetails.getId());
        System.out.println("userId: " + userId);
        return userDetails.getId().equals(userId);
    }
}
