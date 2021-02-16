package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Order;
import com.epam.esm.common.entity.User;
import com.epam.esm.server.entity.OrderRequest;
import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.entity.UserResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.server.mapper.UserMapper;
import com.epam.esm.server.security.AdministratorAllowed;
import com.epam.esm.server.security.UserAllowedOnlyOwn;
import com.epam.esm.service.OrderService;
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
@RequestMapping("/v1/users")
@Validated
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService,
                          OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Retrieve list of {@link User} for appropriate parameters in an amount
     * equal to the <code>limit</code> for page number <code>page</code>.
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link User} represented as list of {@link UserResponse}
     */
    @AdministratorAllowed
    @GetMapping
    public CollectionModel<UserResponse> getUsers(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<UserResponse> users = userService.getUsers(pageNumber, limit)
                .stream().map(UserMapper::convertToResponseWithoutOrders).collect(Collectors.toList());
        users.forEach(u -> {
            u.add(linkTo(methodOn(UserController.class).findById(u.getId())).withSelfRel());
            u.add(linkTo(methodOn(UserController.class)
                    .findOrdersByUserId(u.getId(), 1, 20)).withRel("userOrders").expand());
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
     * Find {@link User} by <code>id</code> and return it represented as {@link UserResponse}
     *
     * @param id specific user's identifier
     * @return certain {@link User} represented as {@link UserResponse}
     */
    @UserAllowedOnlyOwn
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable @Positive Long id) {
        UserResponse userResponse = UserMapper.convertToResponse(userService.findById(id));
        userResponse.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        userResponse.add(linkTo(methodOn(UserController.class)
                .findOrdersByUserId(id, 1, 20)).withRel("userOrders").expand());
        userResponse.getOrders()
                .forEach(o -> {
                    o.add(linkTo(methodOn(OrderController.class).findById(o.getOrderId())).withSelfRel());
                    o.add(linkTo(methodOn(UserController.class).findById(o.getUserId())).withRel("user"));
                    o.add(linkTo(methodOn(GiftCertificateController.class)
                            .findById(o.getGiftCertificateId())).withRel("giftCertificate"));
                });
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Persist new {@link Order} for certain {@link User}
     *
     * @param orderRequest the object that contains data about new
     *              {@link Order} for certain {@link User}
     * @return created {@link Order} represented as {@link OrderResponse}
     */
    @UserAllowedOnlyOwn
    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderResponse> createOrderForUser(
            @PathVariable @Positive Long id, @RequestBody @Valid OrderRequest orderRequest) {
        Order order = orderService.create(id, OrderMapper.convertToEntity(orderRequest));
        OrderResponse orderResponse = OrderMapper.convertToResponse(order);
        orderResponse.add(linkTo(methodOn(UserController.class).createOrderForUser(id, orderRequest)).withSelfRel());
        orderResponse.add(linkTo(methodOn(GiftCertificateController.class)
                .findById(orderResponse.getGiftCertificateId())).withRel("giftCertificate"));
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    /**
     * Find list of {@link Order} for certain {@link User} in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param id specific user's identifier
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of {@link Order} for certain {@link User} represented
     * as list of {@link OrderResponse}
     */
    @UserAllowedOnlyOwn
    @GetMapping("/{id}/orders")
    public CollectionModel<OrderResponse> findOrdersByUserId(
            @PathVariable @Positive Long id,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<OrderResponse> userOrders = orderService.findOrdersByUserId(id, pageNumber, limit)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
        userOrders.forEach(o -> {
            o.add(linkTo(methodOn(OrderController.class).findById(o.getOrderId())).withSelfRel());
            o.add(linkTo(methodOn(UserController.class).findById(o.getUserId())).withRel("user"));
            o.add(linkTo(methodOn(GiftCertificateController.class).findById(o.getGiftCertificateId())).withRel("giftCertificate"));
        });

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
}
