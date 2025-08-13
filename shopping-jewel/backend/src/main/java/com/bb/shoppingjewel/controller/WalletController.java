package com.bb.shoppingjewel.controller;

import com.bb.shoppingjewel.model.User;
import com.bb.shoppingjewel.model.Wallet;
import com.bb.shoppingjewel.repo.UserRepository;
import com.bb.shoppingjewel.repo.WalletRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
//@CrossOrigin(origins = "*")
public class WalletController {
  private final WalletRepository walletRepo;
  private final UserRepository userRepo;
  public WalletController(WalletRepository walletRepo, UserRepository userRepo){ this.walletRepo = walletRepo; this.userRepo = userRepo; }

  @GetMapping
  public ResponseEntity<?> getWallet(Authentication auth){
    User user = userRepo.findByUsername(auth.getName()).orElseThrow();
    Wallet w = walletRepo.findByUserId(user.getId()).orElseGet(() -> {
      Wallet nw = new Wallet(); nw.setUser(user); nw.setBalance(0.0); return walletRepo.save(nw);
    });
    return ResponseEntity.ok(w);
  }

  @PostMapping("/topup")
  public ResponseEntity<?> topup(Authentication auth, @RequestBody Map<String,Object> body){
    double amount = Double.parseDouble(body.get("amount").toString());
    User user = userRepo.findByUsername(auth.getName()).orElseThrow();
    Wallet w = walletRepo.findByUserId(user.getId()).orElseGet(() -> { Wallet nw = new Wallet(); nw.setUser(user); nw.setBalance(0.0); return walletRepo.save(nw); });
    w.setBalance(w.getBalance() + amount);
    walletRepo.save(w);
    return ResponseEntity.ok(w);
  }
}

