package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Order;
import com.epam.esm.server.entity.OrderRequest;
import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.entity.UserResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.server.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
                .stream().map(UserMapper::convertToResponseWithoutOrders).collect(Collectors.toList());
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

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createUserOrder(
            @PathVariable @Positive Long userId, @RequestBody @Valid OrderRequest request) {
        Order order = userService.createUserOrder(userId, OrderMapper.convertToEntity(request));
        return new ResponseEntity<>(OrderMapper.convertToResponse(order), HttpStatus.CREATED);
    }
}
