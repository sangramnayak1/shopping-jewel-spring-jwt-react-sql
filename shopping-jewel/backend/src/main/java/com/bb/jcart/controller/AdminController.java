package com.bb.jcart.controller;

import com.bb.jcart.model.Order;
import com.bb.jcart.model.Wallet;
import com.bb.jcart.model.User;
import com.bb.jcart.repo.OrderRepository;
import com.bb.jcart.repo.ProductRepository;
import com.bb.jcart.repo.UserRepository;
import com.bb.jcart.repo.WalletRepository;
import com.bb.jcart.service.EmailService;
import com.bb.jcart.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "*")
public class AdminController {
	private final ProductRepository productRepo;
	private final OrderRepository orderRepo;
	private final WalletRepository walletRepo;
	private final UserRepository userRepo;
	private final PaymentService paymentService;
	private final EmailService emailService;

	public AdminController(ProductRepository productRepo, OrderRepository orderRepo, WalletRepository walletRepo, UserRepository userRepo, PaymentService paymentService, EmailService emailService){
		this.productRepo = productRepo;
		this.orderRepo = orderRepo;
		this.walletRepo = walletRepo;
		this.userRepo = userRepo;
		this.paymentService = paymentService;
		this.emailService = emailService;
	}

	@GetMapping("/products")
	public List<?> allProducts(){ return productRepo.findAll(); }

	@GetMapping("/orders")
	public List<Order> allOrders(){ return orderRepo.findAll(); }

	@PostMapping("/orders/{orderId}/refund")
	public ResponseEntity<?> refundOrder(@PathVariable Long orderId){
		Order order = orderRepo.findById(orderId).orElseThrow();
		if ("REFUNDED".equalsIgnoreCase(order.getStatus())) return ResponseEntity.badRequest().body(Map.of("error","Already refunded"));
		String refundRef = paymentService.refundPayment(order.getPaymentReference(), order.getTotalAmount());
		User u = order.getUser();
		Wallet wallet = walletRepo.findByUserId(u.getId()).orElseGet(() -> {
			Wallet w = new Wallet(); w.setUser(u); w.setBalance(0.0); return walletRepo.save(w);
		});
		wallet.setBalance(wallet.getBalance() + order.getTotalAmount());
		walletRepo.save(wallet);
		order.setStatus("REFUNDED");
		order.setPaymentReference(order.getPaymentReference() + "|REFUND:" + refundRef);
		orderRepo.save(order);
		// notify user (mock)
		emailService.sendEmail(u.getEmail(), "Your refund processed", "We have refunded $" + order.getTotalAmount() + " to your wallet. Ref: " + refundRef);
		return ResponseEntity.ok(Map.of("message","Refund processed", "refundReference", refundRef));
	}

	// view subscribers
	@GetMapping("/subscribers")
	public List<?> subscribers(){ return productRepo.findAll(); /* placeholder - frontend uses /admin/subscribers but you should inject SubscriberRepository to return actual list */ }
}
