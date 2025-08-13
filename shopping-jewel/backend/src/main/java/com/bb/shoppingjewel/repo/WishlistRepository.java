package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.WishlistItem;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
  List<WishlistItem> findByUserId(Long userId);
  boolean existsByUserIdAndProductId(Long userId, Long productId);
}

