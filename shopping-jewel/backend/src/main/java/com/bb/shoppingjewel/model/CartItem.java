package com.bb.shoppingjewel.model;

import jakarta.persistence.*;

@Entity
public class CartItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long productId;
  private Integer quantity;

  // getters/setters
  public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
  public Long getUserId(){ return userId; } public void setUserId(Long userId){ this.userId = userId; }
  public Long getProductId(){ return productId; } public void setProductId(Long productId){ this.productId = productId; }
  public Integer getQuantity(){ return quantity; } public void setQuantity(Integer quantity){ this.quantity = quantity; }
}

