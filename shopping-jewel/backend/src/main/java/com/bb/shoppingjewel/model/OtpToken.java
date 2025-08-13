package com.bb.shoppingjewel.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class OtpToken {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// we allow OTP per email (not tied to user record until verified)
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String code;

	private OffsetDateTime expiresAt;

	private boolean used = false;

	public Long getId(){ return id; } public void setId(Long id){ this.id = id; }
	public String getEmail(){ return email; } public void setEmail(String email){ this.email = email; }
	public String getCode(){ return code; } public void setCode(String code){ this.code = code; }
	public OffsetDateTime getExpiresAt(){ return expiresAt; } public void setExpiresAt(OffsetDateTime expiresAt){ this.expiresAt = expiresAt; }
	public boolean isUsed(){ return used; } public void setUsed(boolean used){ this.used = used; }
}
