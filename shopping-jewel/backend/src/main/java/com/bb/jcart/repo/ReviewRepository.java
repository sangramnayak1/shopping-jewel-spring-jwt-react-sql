package com.bb.jcart.repo;

import com.bb.jcart.model.Review;
import com.bb.jcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProductOrderByCreatedAtDesc(Product product);
}

