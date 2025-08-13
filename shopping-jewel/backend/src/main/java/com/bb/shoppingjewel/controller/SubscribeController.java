package com.bb.shoppingjewel.controller;

import com.bb.shoppingjewel.model.Subscriber;
import com.bb.shoppingjewel.repo.SubscriberRepository;
import com.bb.shoppingjewel.service.EmailService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/subscribe")
//@CrossOrigin(origins = "*")
public class SubscribeController {
  private final SubscriberRepository repo;
  private final EmailService emailService;

  public SubscribeController(SubscriberRepository repo, EmailService emailService){
    this.repo = repo; this.emailService = emailService;
  }

  @PostMapping
  public ResponseEntity<?> subscribe(@RequestBody Map<String,String> body){
    String email = body.get("email");
    if (email == null || !email.contains("@")) return ResponseEntity.badRequest().body(Map.of("error","Invalid email"));
    if (repo.findByEmail(email).isPresent()) return ResponseEntity.ok(Map.of("message","Already subscribed"));
    Subscriber s = new Subscriber(); s.setEmail(email);
    repo.save(s);
    // send mock confirmation email
    emailService.sendEmail(email, "Subscription confirmed", "Thanks for subscribing to our newsletter!");
    return ResponseEntity.ok(Map.of("message","Subscribed"));
  }
}
