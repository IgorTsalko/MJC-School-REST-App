package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.User;
import com.epam.esm.server.entity.UserRequest;
import com.epam.esm.server.entity.UserResponse;

public class UserMapper {

    public static UserResponse convertToResponse(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setLogin(user.getLogin())
                .setRole(user.getRole().getTitle())
                .setOrders(OrderMapper.convertToResponse(user.getOrders()));
    }

    public static UserResponse convertToResponseWithoutOrders(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setLogin(user.getLogin())
                .setRole(user.getRole().getTitle());
    }

    public static User convertToEntity(UserRequest userRequest) {
        return new User()
                .setFirstName(userRequest.getFirstName())
                .setLastName(userRequest.getLastName())
                .setEmail(userRequest.getEmail())
                .setLogin(userRequest.getLogin())
                .setPassword(userRequest.getPassword());
    }
}
