package com.bb.jcart.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Feedback {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = true)
	private User user; // nullable for anonymous

	@Column(length = 2000)
	private String message;

	private Integer rating; // 1..5

	private OffsetDateTime createdAt = OffsetDateTime.now();

	public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
	public User getUser(){ return user; } public void setUser(User user){ this.user = user; }
	public String getMessage(){ return message; } public void setMessage(String message){ this.message = message; }
	public Integer getRating(){ return rating; } public void setRating(Integer rating){ this.rating = rating; }
	public OffsetDateTime getCreatedAt(){ return createdAt; } public void setCreatedAt(OffsetDateTime createdAt){ this.createdAt = createdAt; }
}
