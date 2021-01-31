package com.epam.esm.repository;

import com.epam.esm.common.entity.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers(int page, int limit);

    User get(Long id);
}
