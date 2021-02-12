package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Token;
import com.epam.esm.server.entity.SignInDataRequest;
import com.epam.esm.server.entity.UserRequest;
import com.epam.esm.server.mapper.SignInDataMapper;
import com.epam.esm.server.mapper.UserMapper;
import com.epam.esm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Token> signIn(@RequestBody @Valid SignInDataRequest signInDataRequest) {
        Token token = authService.signIn(SignInDataMapper.convertToEntity(signInDataRequest));
        return ResponseEntity.ok(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid UserRequest userRequest) {
        authService.signUp(UserMapper.convertToEntity(userRequest));
        return ResponseEntity.noContent().build();
    }
}
