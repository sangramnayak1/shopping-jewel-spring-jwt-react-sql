package com.bb.jcart.model;

import jakarta.persistence.*;

@Entity
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(length = 2000)
  private String description;
  private Double price;
  private String tags; // CSV, e.g. BEST_SELLER,NEW_ARRIVAL
  private String imageUrl;
  private Integer ratingCount = 0;
  private Double ratingAvg = 0.0;

  // getters/setters
  public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
  public String getName(){ return name; } public void setName(String name){ this.name = name; }
  public String getDescription(){ return description; } public void setDescription(String description){ this.description = description; }
  public Double getPrice(){ return price; } public void setPrice(Double price){ this.price = price; }
  public String getTags(){ return tags; } public void setTags(String tags){ this.tags = tags; }
  public String getImageUrl(){ return imageUrl; } public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
  public Integer getRatingCount(){ return ratingCount; } public void setRatingCount(Integer ratingCount){ this.ratingCount = ratingCount; }
  public Double getRatingAvg(){ return ratingAvg; } public void setRatingAvg(Double ratingAvg){ this.ratingAvg = ratingAvg; }
}

