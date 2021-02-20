package com.epam.esm.server.controller;

import com.epam.esm.common.entity.Order;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieve list of {@link Order} for appropriate id in an amount equal to
     * the <code>limit</code> for page number <code>page</code>
     *
     * @param page  number of page
     * @param limit number of entities in the response
     * @return list of {@link Order} represented as list of {@link OrderResponse}
     */
    @AdministratorAllowed
    @GetMapping
    public CollectionModel<OrderResponse> getOrders(
            @RequestParam(required = false, defaultValue = "1") @Positive int page,
            @RequestParam(required = false, defaultValue = "${page.limit-default}") @Min(1) @Max(50) int limit) {
        List<OrderResponse> orders = orderService.getOrders(page, limit)
                .stream()
                .map(OrderMapper::convertToResponse)
                .collect(Collectors.toList());

        assignOrderLinks(orders);

        return CollectionModel.of(orders, generateOrdersLinks(orders.size(), page, limit));
    }

    private void assignOrderLinks(OrderResponse orderResponse) {
        orderResponse.add(
                linkTo(methodOn(OrderController.class).findById(orderResponse.getOrderId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findById(orderResponse.getUserId())).withRel("user"),
                linkTo(methodOn(GiftCertificateController.class)
                        .findById(orderResponse.getGiftCertificateId())).withRel("giftCertificate")
        );
    }

    private void assignOrderLinks(List<OrderResponse> orderResponses) {
        orderResponses.forEach(this::assignOrderLinks);
    }

    private List<Link> generateOrdersLinks(int resultSize, int page, int limit) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(OrderController.class).getOrders(page, limit)).withSelfRel());
        links.add(linkTo(methodOn(OrderController.class).getOrders(1, limit)).withRel("first"));

        if (resultSize == limit) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page + 1, limit)).withRel("next"));
        }
        if (page > 1) {
            links.add(linkTo(methodOn(OrderController.class).getOrders(page - 1, limit)).withRel("previous"));
        }

        return links;
    }

    /**
     * Find {@link Order} by <code>id</code>
     *
     * @param id specific order's identifier
     * @return certain {@link Order} represented as {@link OrderResponse}
     */
    @AdministratorAllowed
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable @Positive Long id) {
        OrderResponse orderResponse = OrderMapper.convertToResponse(orderService.findById(id));
        assignOrderLinks(orderResponse);
        return ResponseEntity.ok(orderResponse);
    }
}
