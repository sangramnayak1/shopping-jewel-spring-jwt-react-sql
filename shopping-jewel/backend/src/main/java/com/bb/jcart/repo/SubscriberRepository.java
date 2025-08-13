package com.bb.jcart.repo;

import com.bb.jcart.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
	Optional<Subscriber> findByEmail(String email);
}

