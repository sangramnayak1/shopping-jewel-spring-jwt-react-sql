package com.bb.jcart.repo;

import com.bb.jcart.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
  List<WishlistItem> findByUserId(Long userId);
  boolean existsByUserIdAndProductId(Long userId, Long productId);
}

