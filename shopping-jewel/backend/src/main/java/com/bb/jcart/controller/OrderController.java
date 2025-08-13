package com.bb.jcart.controller;

import com.bb.jcart.model.Order;
import com.bb.jcart.model.User;
import com.bb.jcart.repo.OrderRepository;
import com.bb.jcart.repo.UserRepository;
import com.bb.jcart.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "*")
public class OrderController {
	private final OrderRepository orderRepo;
	private final UserRepository userRepo;
	private final PaymentService paymentService;

	public OrderController(OrderRepository orderRepo, UserRepository userRepo, PaymentService paymentService){
		this.orderRepo = orderRepo; this.userRepo = userRepo; this.paymentService = paymentService;
	}

	@PostMapping("/place")
	public ResponseEntity<?> placeOrder(@RequestBody Order orderRequest, Authentication auth){
		User user = userRepo.findByUsername(auth.getName()).orElseThrow();
		double total = orderRequest.getItems().stream().mapToDouble(i -> i.getUnitPrice() * i.getQuantity()).sum();
		orderRequest.setUser(user);
		orderRequest.setTotalAmount(total);
		orderRequest.setStatus("CREATED");
		String payRef = paymentService.processPayment("MOCK", total);
		orderRequest.setPaymentReference(payRef);
		orderRequest.setStatus(payRef.contains("FAIL") ? "FAILED" : "PAID");
		Order saved = orderRepo.save(orderRequest);
		return ResponseEntity.ok(saved);
	}

	@GetMapping
	public List<Order> myOrders(Authentication auth){
		User user = userRepo.findByUsername(auth.getName()).orElseThrow();
		return orderRepo.findByUserId(user.getId());
	}

	@PostMapping("/{orderId}/return")
	public ResponseEntity<?> requestReturn(@PathVariable Long orderId, Authentication auth){
		User user = userRepo.findByUsername(auth.getName()).orElseThrow();
		Order order = orderRepo.findById(orderId).orElseThrow();
		if (!order.getUser().getId().equals(user.getId())) return ResponseEntity.status(403).body(Map.of("error","Not your order"));
		if (!"PAID".equalsIgnoreCase(order.getStatus())) return ResponseEntity.badRequest().body(Map.of("error","Only PAID orders can be returned"));
		OffsetDateTime sevenDaysAgo = OffsetDateTime.now().minusDays(7);
		if (order.getCreatedAt().isBefore(sevenDaysAgo)) return ResponseEntity.badRequest().body(Map.of("error","Return period expired"));
		order.setStatus("RETURN_REQUESTED");
		orderRepo.save(order);
		return ResponseEntity.ok(Map.of("message","Return requested"));
	}
}
