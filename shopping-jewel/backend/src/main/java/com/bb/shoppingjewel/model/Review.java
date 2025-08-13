package com.bb.shoppingjewel.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Review {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private User user;

  @ManyToOne(optional = false)
  private Product product;

  private int rating;
  @Column(length = 2000)
  private String comment;

  private OffsetDateTime createdAt = OffsetDateTime.now();

  // getters/setters
  public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
  public User getUser(){ return user; } public void setUser(User user){ this.user = user; }
  public Product getProduct(){ return product; } public void setProduct(Product product){ this.product = product; }
  public int getRating(){ return rating; } public void setRating(int rating){ this.rating = rating; }
  public String getComment(){ return comment; } public void setComment(String comment){ this.comment = comment; }
  public OffsetDateTime getCreatedAt(){ return createdAt; } public void setCreatedAt(OffsetDateTime createdAt){ this.createdAt = createdAt; }
}

