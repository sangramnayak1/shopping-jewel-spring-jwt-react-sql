package com.bb.shoppingjewel.controller;

import com.bb.shoppingjewel.model.WishlistItem;
import com.bb.shoppingjewel.repo.UserRepository;
import com.bb.shoppingjewel.repo.WishlistRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
//@CrossOrigin(origins = "*")
public class WishlistController {
  private final WishlistRepository repo;
  private final UserRepository userRepo;
  public WishlistController(WishlistRepository repo, UserRepository userRepo){ this.repo = repo; this.userRepo = userRepo; }

  private Long getUserId(Authentication auth){ return userRepo.findByUsername(auth.getName()).get().getId(); }

  @GetMapping
  public List<WishlistItem> get(Authentication auth){ return repo.findByUserId(getUserId(auth)); }

  @PostMapping("/add")
  public ResponseEntity<?> add(@RequestBody WishlistItem item, Authentication auth){
    item.setUserId(getUserId(auth));
    if (!repo.existsByUserIdAndProductId(item.getUserId(), item.getProductId())) repo.save(item);
    return ResponseEntity.ok(item);
  }

  @PostMapping("/remove")
  public ResponseEntity<?> remove(@RequestBody WishlistItem item, Authentication auth){
    List<WishlistItem> l = repo.findByUserId(getUserId(auth));
    l.stream().filter(w -> w.getProductId().equals(item.getProductId())).findFirst().ifPresent(repo::delete);
    return ResponseEntity.ok(Map.of("message","removed"));
  }
}

