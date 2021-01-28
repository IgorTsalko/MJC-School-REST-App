package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.User;
import com.epam.esm.server.entity.UserResponse;

public class UserMapper {

    public static UserResponse convertToResponse(User user) {
        UserResponse userResponse = new UserResponse()
                .setId(user.getId())
                .setName(user.getName());

        if (user.getOrders() != null) {
            userResponse.setOrders(OrderMapper.convertToResponse(user.getOrders()));
        }

        return userResponse;
    }
}
