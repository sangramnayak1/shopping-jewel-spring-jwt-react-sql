package com.bb.jcart.controller;

import com.bb.jcart.model.Feedback;
import com.bb.jcart.model.User;
import com.bb.jcart.repo.FeedbackRepository;
import com.bb.jcart.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
//@CrossOrigin(origins = "*")
public class FeedbackController {
	private final FeedbackRepository feedbackRepo;
	private final UserRepository userRepo;

	public FeedbackController(FeedbackRepository feedbackRepo, UserRepository userRepo){
		this.feedbackRepo = feedbackRepo;
		this.userRepo = userRepo;
	}

	@PostMapping
	public ResponseEntity<?> submit(@RequestBody Map<String,Object> body, Authentication auth){
		String message = (String) body.get("message");
		Integer rating = body.get("rating") == null ? null : Integer.parseInt(body.get("rating").toString());
		Feedback f = new Feedback();
		if (auth != null) {
			User u = userRepo.findByUsername(auth.getName()).orElse(null);
			f.setUser(u);
		}
		f.setMessage(message);
		f.setRating(rating);
		feedbackRepo.save(f);
		return ResponseEntity.ok(Map.of("message","submitted"));
	}

	// admin: list all
	@GetMapping
	public List<Feedback> listAll(){
		return feedbackRepo.findAll();
	}
}

