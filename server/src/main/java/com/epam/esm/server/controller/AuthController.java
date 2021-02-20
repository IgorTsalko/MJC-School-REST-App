package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Token;
import com.epam.esm.common.entity.User;
import com.epam.esm.server.entity.CredentialsRequest;
import com.epam.esm.server.entity.UserRequest;
import com.epam.esm.server.mapper.CredentialsMapper;
import com.epam.esm.server.mapper.UserMapper;
import com.epam.esm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authorizes a new {@link User} and returns an authorization {@link Token}
     *
     * @param credentialsRequest user credentials
     * @return authorization {@link Token}
     */
    @PostMapping("/sign-in")
    public ResponseEntity<Token> signIn(@RequestBody @Valid CredentialsRequest credentialsRequest) {
        Token token = authService.signIn(CredentialsMapper.convertToEntity(credentialsRequest));
        return ResponseEntity.ok(token);
    }

    /**
     * Persist a new {@link User} and returns successful status
     * code <code>NO_CONTENT 204</code>
     *
     * @param userRequest new {@link User} data
     * @return successful status code <code>NO_CONTENT 204</code>
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid UserRequest userRequest) {
        authService.signUp(UserMapper.convertToEntity(userRequest));
        return ResponseEntity.noContent().build();
    }
}
