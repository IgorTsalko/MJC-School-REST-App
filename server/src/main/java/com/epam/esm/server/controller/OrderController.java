package com.epam.esm.server.controller;

import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.server.security.AdministratorAllowed;
import com.epam.esm.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieve list of <code>Orders</code> for appropriate id in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param page number of page
     * @param limit number of entities in the response
     * @return list of <code>Orders</code>
     */
    @AdministratorAllowed
    @GetMapping
    public CollectionModel<OrderResponse> getOrders(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<OrderResponse> orders = orderService.getOrders(pageNumber, limit)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
        orders.forEach(o -> {
            o.add(linkTo(methodOn(OrderController.class).get(o.getOrderId())).withSelfRel());
            o.add(linkTo(methodOn(UserController.class).getById(o.getUserId())).withRel("user"));
            o.add(linkTo(methodOn(CertificateController.class).getById(o.getCertificateId())).withRel("certificate"));
        });

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(OrderController.class).getOrders(page, limit)).withSelfRel());
        links.add(linkTo(methodOn(OrderController.class).getOrders(1, limit)).withRel("first"));

        if (page > 0 && orders.size() == limit) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page - 1, limit)).withRel("previous"));
        }

        return CollectionModel.of(orders, links);
    }

    /**
     * Retrieve <code>Order</code> by certain id
     *
     * @param id specific order's identifier
     * @return certain <code>Order</code>
     */
    @AdministratorAllowed
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable @Positive Long id) {
        OrderResponse orderResponse = OrderMapper.convertToResponse(orderService.findById(id));
        orderResponse.add(linkTo(methodOn(OrderController.class).get(id)).withSelfRel());
        orderResponse.add(linkTo(methodOn(UserController.class)
                .getById(orderResponse.getUserId())).withRel("user"));
        orderResponse.add(linkTo(methodOn(CertificateController.class)
                .getById(orderResponse.getCertificateId())).withRel("certificate"));
        return ResponseEntity.ok(orderResponse);
    }
}
