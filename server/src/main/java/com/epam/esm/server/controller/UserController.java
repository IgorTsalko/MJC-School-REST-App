package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Order;
import com.epam.esm.server.entity.OrderRequest;
import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.entity.UserResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.server.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<UserResponse> getAll(
            @RequestParam(required = false) @Positive Integer page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Positive Integer limit) {
        List<UserResponse> users = userService.getAll(page, limit)
                .stream().map(UserMapper::convertToResponseWithoutOrders).collect(Collectors.toList());
        users.forEach(u -> {
            u.add(linkTo(methodOn(UserController.class).get(u.getId())).withSelfRel());
            u.add(linkTo(methodOn(UserController.class).getUserOrders(u.getId(), null, null)).withRel("allOrders").expand());
        });

        List<Link> links = new ArrayList<>();
        if (page == null) {
            links.add(linkTo(methodOn(UserController.class).getAll(null, null)).withSelfRel().expand());
        } else {
            links.add(linkTo(methodOn(UserController.class).getAll(page, limit)).withSelfRel());
        }

        links.add(linkTo(methodOn(UserController.class).getAll(1, limit)).withRel("first"));

        if (page != null) {
            links.add(linkTo(methodOn(UserController.class).getAll(page + 1, limit)).withRel("next"));
            if (page > 1) {
                links.add(linkTo(methodOn(UserController.class).getAll(page - 1, limit)).withRel("previous"));
            }
        }

        return CollectionModel.of(users, links);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable @Positive Long id) {
        UserResponse userResponse = UserMapper.convertToResponse(userService.get(id));
        userResponse.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        userResponse.add(linkTo(methodOn(UserController.class)
                .getUserOrders(id, null, null)).withRel("allOrders").expand());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}/orders")
    public CollectionModel<OrderResponse> getUserOrders(
            @PathVariable @Positive Long id, @RequestParam(required = false) @Positive Integer page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Positive Integer limit) {
        List<OrderResponse> userOrders = userService.getUserOrders(id, page, limit)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
        userOrders.forEach(o -> o.add(linkTo(methodOn(OrderController.class).get(o.getOrderId())).withSelfRel()));

        List<Link> links = new ArrayList<>();
        if (page == null) {
            links.add(linkTo(methodOn(UserController.class).getAll(null, null)).withSelfRel().expand());
        } else {
            links.add(linkTo(methodOn(UserController.class).getAll(page, limit)).withSelfRel());
        }

        links.add(linkTo(methodOn(UserController.class).getAll(1, limit)).withRel("first"));

        if (page != null) {
            links.add(linkTo(methodOn(UserController.class).getAll(page + 1, limit)).withRel("next"));
            if (page > 1) {
                links.add(linkTo(methodOn(UserController.class).getAll(page - 1, limit)).withRel("previous"));
            }
        }

        return CollectionModel.of(userOrders, links);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createUserOrder(
            @PathVariable @Positive Long userId, @RequestBody @Valid OrderRequest request) {
        Order order = userService.createUserOrder(userId, OrderMapper.convertToEntity(request));
        OrderResponse orderResponse = OrderMapper.convertToResponse(order);
        orderResponse.add(linkTo(methodOn(UserController.class).createUserOrder(userId, request)).withSelfRel());
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}
