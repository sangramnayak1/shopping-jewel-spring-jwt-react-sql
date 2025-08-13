package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.Subscriber;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
	Optional<Subscriber> findByEmail(String email);
}

