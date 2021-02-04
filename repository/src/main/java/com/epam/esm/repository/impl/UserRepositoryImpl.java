package com.epam.esm.repository.impl;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.entity.User;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String JPQL_SELECT_ALL = "from User u order by u.id";
    private static final String JPQL_SELECT_BY_ID = "select distinct u from User u left join fetch u.orders where u.id=:id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers(int page, int limit) {
        return entityManager.createQuery(JPQL_SELECT_ALL, User.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public User get(Long id) {
        return entityManager.createQuery(JPQL_SELECT_BY_ID, User.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.USER_NOT_FOUND, id));
    }
}
