package com.bb.jcart.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PaymentService {
  public String processPayment(String method, double amount){
    int cents = (int) Math.round(amount * 100);
    boolean success = (cents % 2) == 0;
    if (!success) return "MOCKPAY-FAIL-" + UUID.randomUUID().toString();
    return "MOCKPAY-" + UUID.randomUUID().toString();
  }

  public String refundPayment(String originalPaymentReference, double amount) {
    return "MOCKREFUND-" + UUID.randomUUID().toString();
  }
}

