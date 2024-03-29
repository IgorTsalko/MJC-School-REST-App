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
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        List<UserResponse> users = userService.getUsers(page, limit)
                .stream()
                .map(UserMapper::convertToResponseWithoutOrders)
                .collect(Collectors.toList());

        users.forEach(this::assignUserLinks);

        return CollectionModel.of(users, generateUsersLinks(users.size(), page, limit));
    }

    private void assignUserLinks(UserResponse userResponse) {
        userResponse.add(
                linkTo(methodOn(UserController.class).findById(userResponse.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class)
                        .findOrdersByUserId(userResponse.getId(), 1, 20)).withRel("userOrders").expand()
        );
    }

    private List<Link> generateUsersLinks(int resultSize, int page, int limit) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(UserController.class).getUsers(page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(UserController.class).getUsers(1, limit)).withRel("first"));

        if (resultSize == limit) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(UserController.class).getUsers(page - 1, limit)).withRel("previous"));
        }

        return links;
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
        assignUserLinks(userResponse);
        userResponse.getOrders().forEach(this::assignUserOrdersLinks);
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
        assignUserOrdersLinks(orderResponse);
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
            @RequestParam(required = false, defaultValue = "1") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {

        List<OrderResponse> userOrders = orderService.findOrdersByUserId(id, page, limit)
                .stream()
                .map(OrderMapper::convertToResponse)
                .collect(Collectors.toList());

        userOrders.forEach(this::assignUserOrdersLinks);

        return CollectionModel.of(userOrders, generateUserOrdersLinks(userOrders.size(), id, page, limit));
    }

    private void assignUserOrdersLinks(OrderResponse orderResponse) {
        orderResponse.add(
                linkTo(methodOn(OrderController.class).findById(orderResponse.getOrderId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findById(orderResponse.getUserId())).withRel("user"),
                linkTo(methodOn(GiftCertificateController.class)
                        .findById(orderResponse.getGiftCertificateId())).withRel("giftCertificate")
        );
    }

    private List<Link> generateUserOrdersLinks(int resultSize, Long id, int page, int limit) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(UserController.class).findOrdersByUserId(id, page, limit)).withSelfRel().expand());
        links.add(linkTo(methodOn(UserController.class).findOrdersByUserId(id, page, limit)).withRel("first"));

        if (resultSize == limit) {
            links.add(linkTo(methodOn(UserController.class).findOrdersByUserId(id, page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(UserController.class).findOrdersByUserId(id, page - 1, limit)).withRel("previous"));
        }

        return links;
    }
}
