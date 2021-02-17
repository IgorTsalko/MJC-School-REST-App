package com.epam.esm.repository;

import com.epam.esm.common.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUserId(Long userId, Pageable pageable);
}
