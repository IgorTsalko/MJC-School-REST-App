package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.Order;
import com.epam.esm.server.entity.OrderRequest;
import com.epam.esm.server.entity.OrderResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse convertToResponse(Order order) {
        return new OrderResponse()
                .setOrderId(order.getOrderId())
                .setUserId(order.getUserId())
                .setCertificateId(order.getCertificateId())
                .setPrice(order.getPrice())
                .setCreateDate(order.getCreateDate());
    }

    public static List<OrderResponse> convertToResponse(List<Order> orders) {
        return orders.stream().map(OrderMapper::convertToResponse).collect(Collectors.toList());
    }

    public static Order convertToEntity(OrderRequest orderRequest) {
        return new Order().setCertificateId(orderRequest.getCertificateId());
    }
}
