package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.OtpToken;

import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
	Optional<OtpToken> findTopByEmailAndCodeOrderByIdDesc(String email, String code);
}
