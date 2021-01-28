package com.epam.esm.server.controller;

import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.entity.UserResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.server.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll()
                .stream().map(UserMapper::convertToResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable @Positive Long id) {
        return UserMapper.convertToResponse(userService.get(id));
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponse> getUserOrders(@PathVariable @Positive Long id) {
        return userService.getUserOrders(id)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
    }
}
