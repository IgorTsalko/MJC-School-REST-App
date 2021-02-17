package com.epam.esm.service;

import com.epam.esm.common.entity.Credentials;
import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.User;

public interface AuthService {

    /**
     * Authorizes a new {@link User} and returns an authorization {@link Token}
     *
     * @param credentials user credentials
     */
    Token signIn(Credentials credentials);

    /**
     * Persist a new {@link User}
     *
     * @param user the object that contain properties for new {@link User}
     */
    void signUp(User user);
}
