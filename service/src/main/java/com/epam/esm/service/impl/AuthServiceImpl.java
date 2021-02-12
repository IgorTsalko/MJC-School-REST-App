package com.epam.esm.service.impl;

import com.epam.esm.common.entity.SignInData;
import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.User;
import com.epam.esm.common.exception.WrongCredentialException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.AuthService;
import com.epam.esm.service.util.TokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           TokenUtil tokenUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Token signIn(SignInData signInData) {
        User user = userRepository.findByLogin(signInData.getLogin());

        if (user == null || !passwordEncoder.matches(signInData.getPassword(), user.getPassword())) {
            throw new WrongCredentialException();
        }

        String token = tokenUtil.generateAccessToken(user);
        return new Token(token);
    }

    @Override
    public void signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
