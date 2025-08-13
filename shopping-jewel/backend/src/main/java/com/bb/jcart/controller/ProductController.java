package com.bb.jcart.controller;

import com.bb.jcart.model.Product;
import com.bb.jcart.repo.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
//@CrossOrigin(origins = "*")
public class ProductController {
  private final ProductRepository repo;
  public ProductController(ProductRepository repo){ this.repo = repo; }

  @GetMapping
  public List<Product> list(@RequestParam(value = "q", required = false) String q) {
    if (q != null && !q.isEmpty()) return repo.findByNameContainingIgnoreCase(q);
    return repo.findAll();
  }

  @GetMapping("/tag/{tag}")
  public List<Product> byTag(@PathVariable String tag) {
    return repo.findByTag(tag.toUpperCase());
  }

  @GetMapping("/{id}")
  public Product get(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }
}

