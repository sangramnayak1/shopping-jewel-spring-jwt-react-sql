package com.bb.jcart.model;

import jakarta.persistence.*;

@Entity
public class Wallet {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne private User user;
	private Double balance = 0.0;

	// getters/setters
	public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
	public User getUser(){ return user; } public void setUser(User user){ this.user = user; }
	public Double getBalance(){ return balance; } public void setBalance(Double balance){ this.balance = balance; }
}
