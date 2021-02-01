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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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

    /**
     * Retrieve <code>Users</code> for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Users</code>
     */
    @GetMapping
    public CollectionModel<UserResponse> getUsers(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(0) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<UserResponse> users = userService.getUsers(pageNumber, limit)
                .stream().map(UserMapper::convertToResponseWithoutOrders).collect(Collectors.toList());
        users.forEach(u -> {
            u.add(linkTo(methodOn(UserController.class).get(u.getId())).withSelfRel());
            u.add(linkTo(methodOn(UserController.class)
                    .getUserOrders(u.getId(), page, limit)).withRel("allOrders").expand());
        });

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(UserController.class).getUsers(page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(UserController.class).getUsers(1, limit)).withRel("first"));

        if (page > 0 && users.size() == limit) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page - 1, limit)).withRel("previous"));
        }

        return CollectionModel.of(users, links);
    }

    /**
     * Retrieve <code>User</code> by certain id
     *
     * @param id specific user's identifier
     * @return certain <code>User</code>
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable @Positive Long id) {
        UserResponse userResponse = UserMapper.convertToResponse(userService.get(id));
        userResponse.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        userResponse.add(linkTo(methodOn(UserController.class)
                .getUserOrders(id, 1, 20)).withRel("allOrders").expand());
        userResponse.getOrders()
                .forEach(o -> o.add(linkTo(methodOn(OrderController.class).get(o.getOrderId())).withSelfRel()));
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Retrieve list of <code>Orders</code> for certain <code>User</code> in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param id specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Orders</code> for certain <code>User</code>
     */
    @GetMapping("/{id}/orders")
    public CollectionModel<OrderResponse> getUserOrders(
            @PathVariable @Positive Long id,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(0) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<OrderResponse> userOrders = userService.getUserOrders(id, pageNumber, limit)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
        userOrders.forEach(o -> o.add(linkTo(methodOn(OrderController.class).get(o.getOrderId())).withSelfRel()));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(UserController.class).getUsers(page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(UserController.class).getUsers(1, limit)).withRel("first"));

        if (page > 0 && userOrders.size() == limit) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page - 1, limit)).withRel("previous"));
        }

        return CollectionModel.of(userOrders, links);
    }

    /**
     * Persist new <code>Order</code> for certain <code>User</code>
     *
     * @param orderRequest the object that contains data about new
     *              <code>Order</code> for certain <code>User</code>
     * @return created <code>Order</code>
     */
    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createUserOrder(
            @PathVariable @Positive Long userId, @RequestBody @Valid OrderRequest orderRequest) {
        Order order = userService.createUserOrder(userId, OrderMapper.convertToEntity(orderRequest));
        OrderResponse orderResponse = OrderMapper.convertToResponse(order);
        orderResponse.add(linkTo(methodOn(UserController.class).createUserOrder(userId, orderRequest)).withSelfRel());
        orderResponse.add(linkTo(methodOn(CertificateController.class)
                .get(orderResponse.getCertificateId())).withRel("certificate"));
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}
