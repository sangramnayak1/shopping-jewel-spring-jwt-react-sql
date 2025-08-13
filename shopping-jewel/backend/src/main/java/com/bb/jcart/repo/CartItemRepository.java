package com.bb.jcart.repo;

import com.bb.jcart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByUserId(Long userId);
  void deleteByUserIdAndProductId(Long userId, Long productId);
}

