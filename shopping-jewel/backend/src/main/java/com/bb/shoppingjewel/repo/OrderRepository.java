package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserId(Long userId);
}

