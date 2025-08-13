package com.bb.jcart.controller;

import com.bb.jcart.model.Order;
import com.bb.jcart.model.Wallet;
import com.bb.jcart.repo.OrderRepository;
import com.bb.jcart.repo.WalletRepository;
import com.bb.jcart.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/refund")
public class RefundController {

	private final OrderRepository orderRepo;
	private final WalletRepository walletRepo;
	private final PaymentService paymentService;

	@Autowired
	public RefundController(OrderRepository orderRepo, WalletRepository walletRepo, PaymentService paymentService) {
		this.orderRepo = orderRepo;
		this.walletRepo = walletRepo;
		this.paymentService = paymentService;
	}

	@PostMapping("/orders/{orderId}/refund")
	public ResponseEntity<?> refundOrder(@PathVariable Long orderId) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

		if ("REFUNDED".equalsIgnoreCase(order.getStatus())) {
			return ResponseEntity.badRequest()
					.body(Map.of("error", "Already refunded"));
		}

		// Call payment service to perform (mock) refund
		String refundRef = paymentService.refundPayment(order.getPaymentReference(), order.getTotalAmount());

		// Credit user's wallet
		Long userId = order.getUser().getId();
		Wallet wallet = walletRepo.findByUserId(userId).orElseGet(() -> {
			Wallet w = new Wallet();
			w.setUser(order.getUser());
			w.setBalance(0.0);
			return walletRepo.save(w);
		});
		wallet.setBalance(wallet.getBalance() + order.getTotalAmount());
		walletRepo.save(wallet);

		// Update order status and payment reference
		order.setStatus("REFUNDED");
		order.setPaymentReference(order.getPaymentReference() + "|REFUND:" + refundRef);
		orderRepo.save(order);

		return ResponseEntity.ok(Map.of(
				"message", "Refund processed",
				"refundReference", refundRef
				));
	}
}

