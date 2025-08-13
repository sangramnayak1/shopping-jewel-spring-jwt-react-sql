package com.bb.jcart.service;

import com.bb.jcart.model.Product;
import com.bb.jcart.model.Review;
import com.bb.jcart.repo.ProductRepository;
import com.bb.jcart.repo.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
  private final ReviewRepository reviewRepo;
  private final ProductRepository productRepo;
  public ReviewService(ReviewRepository reviewRepo, ProductRepository productRepo){ this.reviewRepo = reviewRepo; this.productRepo = productRepo; }

  @Transactional
  public Review addReview(Review r){
    Review saved = reviewRepo.save(r);
    recalcProductRating(saved.getProduct());
    return saved;
  }

  private void recalcProductRating(Product p){
    List<Review> all = reviewRepo.findByProductOrderByCreatedAtDesc(p);
    int count = all.size();
    double avg = all.stream().mapToInt(Review::getRating).average().orElse(0.0);
    p.setRatingCount(count);
    p.setRatingAvg(avg);
    productRepo.save(p);
  }
}

