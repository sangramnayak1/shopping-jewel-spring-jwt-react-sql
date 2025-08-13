package com.bb.jcart.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Subscriber {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	private OffsetDateTime subscribedAt = OffsetDateTime.now();

	public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
	public String getEmail(){ return email; } public void setEmail(String email){ this.email = email; }
	public OffsetDateTime getSubscribedAt(){ return subscribedAt; } public void setSubscribedAt(OffsetDateTime subscribedAt){ this.subscribedAt = subscribedAt; }
}
