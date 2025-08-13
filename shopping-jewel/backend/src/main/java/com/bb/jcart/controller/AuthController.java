package com.bb.jcart.controller;

import com.bb.jcart.model.OtpToken;
import com.bb.jcart.model.User;
import com.bb.jcart.repo.OtpTokenRepository;
import com.bb.jcart.repo.UserRepository;
import com.bb.jcart.security.JwtUtil;
import com.bb.jcart.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
public class AuthController {
  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final OtpTokenRepository otpRepo;
  private final EmailService emailService;

  public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, OtpTokenRepository otpRepo, EmailService emailService){
    this.userRepo = userRepo; this.passwordEncoder = passwordEncoder; this.jwtUtil = jwtUtil; this.otpRepo = otpRepo; this.emailService = emailService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Map<String,String> body){
    String username = body.get("username");
    String email = body.get("email");
    String password = body.get("password");
    if (userRepo.existsByUsername(username)) return ResponseEntity.badRequest().body("Username exists");
    if (userRepo.existsByEmail(email)) return ResponseEntity.badRequest().body("Email exists");
    User u = new User(); u.setUsername(username); u.setEmail(email);
    u.setPassword(passwordEncoder.encode(password));
    userRepo.save(u);
    String token = jwtUtil.generateToken(username);
    return ResponseEntity.ok(Map.of("token", token, "username", username));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String,String> body){
    String username = body.get("username");
    String password = body.get("password");
    return userRepo.findByUsername(username).map(u -> {
      if (passwordEncoder.matches(password, u.getPassword())){
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
      }
      return ResponseEntity.status(401).body("Invalid credentials");
    }).orElse(ResponseEntity.status(401).body("Invalid credentials"));
  }

  // ========== OTP: request & verify (email OTP) ==========
  @PostMapping("/request-otp")
  public ResponseEntity<?> requestOtp(@RequestBody Map<String,String> body) {
    String email = body.get("email");
    if (email == null || !email.contains("@")) return ResponseEntity.badRequest().body(Map.of("error","Invalid email"));
    // generate 6-digit code
    String code = String.format("%06d", new Random().nextInt(1_000_000));
    OtpToken t = new OtpToken();
    t.setEmail(email);
    t.setCode(code);
    t.setExpiresAt(OffsetDateTime.now().plusMinutes(10));
    otpRepo.save(t);
    emailService.sendEmail(email, "Your verification code", "Your OTP code is: " + code + " (valid 10 minutes)");
    return ResponseEntity.ok(Map.of("message","otp_sent"));
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<?> verifyOtp(@RequestBody Map<String,String> body){
    String email = body.get("email");
    String code = body.get("code");
    if (email == null || code == null) return ResponseEntity.badRequest().body(Map.of("error","Missing fields"));
    OtpToken token = otpRepo.findTopByEmailAndCodeOrderByIdDesc(email, code).orElse(null);
    if (token == null) return ResponseEntity.status(404).body(Map.of("error","OTP not found"));
    if (token.isUsed()) return ResponseEntity.badRequest().body(Map.of("error","OTP already used"));
    if (token.getExpiresAt().isBefore(OffsetDateTime.now())) return ResponseEntity.badRequest().body(Map.of("error","OTP expired"));
    token.setUsed(true);
    otpRepo.save(token);
    // optionally, auto-register user if not exists — here we just return success
    return ResponseEntity.ok(Map.of("message","verified"));
  }
}
