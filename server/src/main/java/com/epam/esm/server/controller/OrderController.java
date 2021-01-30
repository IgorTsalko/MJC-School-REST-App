package com.epam.esm.server.controller;

import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
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

    @GetMapping
    public CollectionModel<OrderResponse> getAll(
            @RequestParam(required = false) @Positive Integer page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Positive Integer limit) {
        List<OrderResponse> orders = orderService.getAll(page, limit)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
        orders.forEach(o -> o.add(linkTo(methodOn(OrderController.class).get(o.getOrderId())).withSelfRel()));

        List<Link> links = new ArrayList<>();
        if (page == null) {
            links.add(linkTo(methodOn(OrderController.class).getAll(null, null)).withSelfRel().expand());
        } else {
            links.add(linkTo(methodOn(OrderController.class).getAll(page, limit)).withSelfRel());
        }

        links.add(linkTo(methodOn(OrderController.class).getAll(1, limit)).withRel("first"));

        if (page != null) {
            links.add(linkTo(methodOn(OrderController.class).getAll(page + 1, limit)).withRel("next"));
            if (page > 1) {
                links.add(linkTo(methodOn(OrderController.class).getAll(page - 1, limit)).withRel("previous"));
            }
        }

        return CollectionModel.of(orders, links);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable @Positive Long id) {
        OrderResponse orderResponse = OrderMapper.convertToResponse(orderService.get(id));
        orderResponse.add(linkTo(methodOn(OrderController.class).get(id)).withSelfRel());
        return ResponseEntity.ok(orderResponse);
    }
}
