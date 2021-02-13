package com.epam.esm.service;

import com.epam.esm.common.entity.SignInData;
import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.User;

public interface AuthService {

    /**
     * User authorization
     *
     * @param signInData the object that contain properties for user sign in.
     */
    Token signIn(SignInData signInData);

    /**
     * Sign up new {@link User}
     *
     * @param user the object that contain properties for new {@link User}
     */
    void signUp(User user);
}
