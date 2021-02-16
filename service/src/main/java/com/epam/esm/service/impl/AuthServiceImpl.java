package com.epam.esm.service.impl;

import com.epam.esm.common.entity.Credentials;
import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.AuthService;
import com.epam.esm.service.TokenHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenHandler tokenHandler;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           TokenHandler tokenHandler,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenHandler = tokenHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Token signIn(Credentials credentials) {
        User user = userRepository.findByLogin(credentials.getLogin());

        if (user == null || !passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials: " + credentials);
        }

        return tokenHandler.generateAccessToken(user);
    }

    @Override
    public void signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
    }
}
