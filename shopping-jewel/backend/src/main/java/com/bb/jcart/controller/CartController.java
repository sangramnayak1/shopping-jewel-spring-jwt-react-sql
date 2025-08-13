package com.bb.jcart.controller;

import com.bb.jcart.model.CartItem;
import com.bb.jcart.repo.CartItemRepository;
import com.bb.jcart.repo.ProductRepository;
import com.bb.jcart.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
//@CrossOrigin(origins = "*")
public class CartController {
  private final CartItemRepository cartRepo;
  private final ProductRepository productRepo;
  private final UserRepository userRepo;

  public CartController(CartItemRepository cartRepo, ProductRepository productRepo, UserRepository userRepo){
    this.cartRepo = cartRepo; this.productRepo = productRepo; this.userRepo = userRepo;
  }

  private Long getUserId(Authentication auth){
    return userRepo.findByUsername(auth.getName()).get().getId();
  }

  @GetMapping
  public List<CartItem> getCart(Authentication auth){
    Long uid = getUserId(auth);
    return cartRepo.findByUserId(uid);
  }

  @PostMapping("/add")
  public ResponseEntity<?> add(@RequestBody CartItem item, Authentication auth){
    Long uid = getUserId(auth);
    item.setUserId(uid);
    cartRepo.save(item);
    return ResponseEntity.ok(item);
  }

  @PostMapping("/remove")
  public ResponseEntity<?> remove(@RequestBody CartItem item, Authentication auth){
    Long uid = getUserId(auth);
    cartRepo.deleteByUserIdAndProductId(uid, item.getProductId());
    return ResponseEntity.ok(Map.of("message","removed"));
  }
}

