package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByUserId(Long userId);
  void deleteByUserIdAndProductId(Long userId, Long productId);
}

