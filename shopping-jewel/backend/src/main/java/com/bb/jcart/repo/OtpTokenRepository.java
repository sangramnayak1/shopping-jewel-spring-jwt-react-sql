package com.bb.jcart.repo;

import com.bb.jcart.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
	Optional<OtpToken> findTopByEmailAndCodeOrderByIdDesc(String email, String code);
}
