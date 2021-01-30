package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.User;
import com.epam.esm.server.entity.UserResponse;

public class UserMapper {

    public static UserResponse convertToResponse(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setName(user.getName())
                .setOrders(OrderMapper.convertToResponse(user.getOrders()));
    }

    public static UserResponse convertToResponseWithoutOrders(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setName(user.getName());
    }
}
