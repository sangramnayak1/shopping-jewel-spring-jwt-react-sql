package com.bb.shoppingjewel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bb.shoppingjewel.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	
}
