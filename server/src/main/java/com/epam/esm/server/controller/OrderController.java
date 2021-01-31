package com.epam.esm.server.controller;

import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.mapper.OrderMapper;
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

    @GetMapping
    public CollectionModel<OrderResponse> getOrders(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(0) @Max(50) int limit) {
        int pageNumber = page == 0 ? 1 : page;
        List<OrderResponse> orders = orderService.getOrders(pageNumber, limit)
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
        orders.forEach(o -> o.add(linkTo(methodOn(OrderController.class).get(o.getOrderId())).withSelfRel()));

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable @Positive Long id) {
        OrderResponse orderResponse = OrderMapper.convertToResponse(orderService.get(id));
        orderResponse.add(linkTo(methodOn(OrderController.class).get(id)).withSelfRel());
        return ResponseEntity.ok(orderResponse);
    }
}
