package com.epam.esm.server.mapper;

import com.epam.esm.common.entity.Credentials;
import com.epam.esm.server.entity.CredentialsRequest;

public class CredentialsMapper {

    public static Credentials convertToEntity(CredentialsRequest credentialsRequest) {
        return new Credentials()
                .setLogin(credentialsRequest.getLogin())
                .setPassword(credentialsRequest.getPassword());
    }
}
