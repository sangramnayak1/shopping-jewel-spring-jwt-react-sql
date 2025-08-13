package com.bb.jcart.service;

import com.bb.jcart.model.Product;
import com.bb.jcart.model.User;
import com.bb.jcart.repo.ProductRepository;
import com.bb.jcart.repo.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
  private final ProductRepository productRepo;
  private final UserRepository userRepo;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public DataLoader(ProductRepository productRepo, UserRepository userRepo){ this.productRepo = productRepo; this.userRepo = userRepo; }

  @Override
  public void run(ApplicationArguments args) {
    if (productRepo.count() == 0) {
      Product p1 = new Product();
      p1.setName("Sample T-Shirt");
      p1.setDescription("Comfortable cotton tee");
      p1.setPrice(19.99);
      p1.setTags("NEW_ARRIVAL,POPULAR");
      p1.setImageUrl("https://via.placeholder.com/200");
      productRepo.save(p1);

      Product p2 = new Product();
      p2.setName("Running Shoes");
      p2.setDescription("Lightweight shoes");
      p2.setPrice(59.99);
      p2.setTags("BEST_SELLER,OFFER");
      p2.setImageUrl("https://via.placeholder.com/200");
      productRepo.save(p2);
    }

    // seed admin user if not present
    if (!userRepo.existsByUsername("admin")) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setEmail("admin@example.com");
      admin.setPassword(encoder.encode("admin123"));
      admin.setRoles("ROLE_ADMIN,ROLE_USER");
      userRepo.save(admin);
    }
  }
}
