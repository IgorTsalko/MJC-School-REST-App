package com.epam.esm.server.controller;

import com.epam.esm.server.entity.OrderResponse;
import com.epam.esm.server.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> getAll() {
        return orderService.getAll()
                .stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable @Positive Long id) {
        return OrderMapper.convertToResponse(orderService.get(id));
    }
}
