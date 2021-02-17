package com.epam.esm.service.security;

import com.epam.esm.repository.UserRepository;
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
        return UserDetailsFactory.create(
                userRepository
                        .findByLogin(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Username: " + login + " not found"))
        );
    }
}
