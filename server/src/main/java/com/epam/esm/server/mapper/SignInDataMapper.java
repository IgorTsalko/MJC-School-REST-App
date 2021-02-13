package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.SignInData;
import com.epam.esm.server.entity.SignInDataRequest;

public class SignInDataMapper {

    public static SignInData convertToEntity(SignInDataRequest signInDataRequest) {
        return new SignInData()
                .setLogin(signInDataRequest.getLogin())
                .setPassword(signInDataRequest.getPassword());
    }
}
