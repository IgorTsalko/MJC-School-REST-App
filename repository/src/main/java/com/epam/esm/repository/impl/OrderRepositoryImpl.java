package com.epam.esm.repository.impl;

import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.entity.Order;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String JPQL_SELECT_ALL = "from Order o order by o.id";
    private static final String JPQL_SELECT_ALL_BY_USER_ID = "from Order o where o.userId=:id order by o.id";

    @Override
    public List<Order> retrieveOrders(int page, int limit) {
        return entityManager.createQuery(JPQL_SELECT_ALL, Order.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Order findById(Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new EntityNotFoundException(ErrorDefinition.ORDER_NOT_FOUND, id);
        }
        return order;
    }

    @Override
    public List<Order> retrieveUserOrders(Long userId, int page, int limit) {
        return entityManager.createQuery(JPQL_SELECT_ALL_BY_USER_ID, Order.class)
                .setParameter("id", userId)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Order save(Order order) {
        order.setCreateDate(LocalDateTime.now());
        entityManager.persist(order);
        return order;
    }
}
