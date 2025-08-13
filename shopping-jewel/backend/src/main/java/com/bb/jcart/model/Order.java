package com.bb.jcart.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne private User user;
  private OffsetDateTime createdAt = OffsetDateTime.now();
  private Double totalAmount;
  private String status;
  @OneToMany(cascade = CascadeType.ALL)
  private List<OrderItem> items;
  private String paymentReference;

  // getters/setters
  public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
  public User getUser(){ return user; } public void setUser(User user){ this.user = user; }
  public OffsetDateTime getCreatedAt(){ return createdAt; } public void setCreatedAt(OffsetDateTime createdAt){ this.createdAt = createdAt; }
  public Double getTotalAmount(){ return totalAmount; } public void setTotalAmount(Double totalAmount){ this.totalAmount = totalAmount; }
  public String getStatus(){ return status; } public void setStatus(String status){ this.status = status; }
  public List<OrderItem> getItems(){ return items; } public void setItems(List<OrderItem> items){ this.items = items; }
  public String getPaymentReference(){ return paymentReference; } public void setPaymentReference(String paymentReference){ this.paymentReference = paymentReference; }
}

