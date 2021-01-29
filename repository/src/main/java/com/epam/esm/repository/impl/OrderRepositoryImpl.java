package com.epam.esm.repository.impl;

import com.epam.esm.common.ErrorDefinition;
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

    private static final String JPQL_SELECT_ALL = "from Order";
    private static final String JPQL_SELECT_ALL_BY_USER_ID = "from Order o where o.userId=:id";

    @Override
    public List<Order> getAll(Integer page, Integer limit) {
        int firstResult = page == null ? 0 : (page - 1) * limit;
        return entityManager.createQuery(JPQL_SELECT_ALL, Order.class)
                .setFirstResult(firstResult)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Order get(Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new EntityNotFoundException(ErrorDefinition.ORDER_NOT_FOUND, id);
        }
        return order;
    }

    @Override
    public List<Order> getAllUserOrders(Long userId) {
        return entityManager.createQuery(JPQL_SELECT_ALL_BY_USER_ID, Order.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public List<Order> getUserOrders(Long userId, Integer page, Integer limit) {
        int firstResult = page == null ? 0 : (page - 1) * limit;
        return entityManager.createQuery(JPQL_SELECT_ALL_BY_USER_ID, Order.class)
                .setParameter("id", userId)
                .setFirstResult(firstResult)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Order createUserOrder(Order order) {
        order.setCreateDate(LocalDateTime.now());
        entityManager.persist(order);
        return order;
    }
}
