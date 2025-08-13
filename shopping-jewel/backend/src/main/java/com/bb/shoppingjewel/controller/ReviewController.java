package com.bb.shoppingjewel.controller;

import com.bb.shoppingjewel.model.Product;
import com.bb.shoppingjewel.model.Review;
import com.bb.shoppingjewel.model.User;
import com.bb.shoppingjewel.repo.ProductRepository;
import com.bb.shoppingjewel.repo.ReviewRepository;
import com.bb.shoppingjewel.repo.UserRepository;
import com.bb.shoppingjewel.service.ReviewService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "*")
public class ReviewController {
  private final ReviewRepository reviewRepo;
  private final ProductRepository productRepo;
  private final UserRepository userRepo;
  private final ReviewService reviewService;

  public ReviewController(ReviewRepository reviewRepo, ProductRepository productRepo, UserRepository userRepo, ReviewService reviewService){
    this.reviewRepo = reviewRepo; this.productRepo = productRepo; this.userRepo = userRepo; this.reviewService = reviewService;
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<List<Review>> byProduct(@PathVariable Long productId){
    Product p = productRepo.findById(productId).orElseThrow();
    return ResponseEntity.ok(reviewRepo.findByProductOrderByCreatedAtDesc(p));
  }

  @PostMapping("/add/{productId}")
  public ResponseEntity<?> add(@PathVariable Long productId, @RequestBody Review body, Authentication auth){
    User user = userRepo.findByUsername(auth.getName()).orElseThrow();
    Product p = productRepo.findById(productId).orElseThrow();
    if (body.getRating() < 1 || body.getRating() > 5) return ResponseEntity.badRequest().body("Rating must be 1..5");
    Review r = new Review();
    r.setUser(user); r.setProduct(p); r.setRating(body.getRating()); r.setComment(body.getComment());
    Review saved = reviewService.addReview(r);
    return ResponseEntity.ok(saved);
  }
}

