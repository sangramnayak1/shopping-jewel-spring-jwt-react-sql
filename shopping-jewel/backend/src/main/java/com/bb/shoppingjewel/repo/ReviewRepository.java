package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.Product;
import com.bb.shoppingjewel.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProductOrderByCreatedAtDesc(Product product);
}

