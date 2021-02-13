package com.epam.esm.service.impl;

import com.epam.esm.common.entity.User;
import com.epam.esm.common.exception.BadCredentialsException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.util.UserDetailsFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new BadCredentialsException();
        }

        return UserDetailsFactory.create(user);
    }
}
