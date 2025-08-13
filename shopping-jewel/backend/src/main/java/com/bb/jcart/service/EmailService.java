package com.bb.jcart.service;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {
	// mock in-memory store of sent messages for admin viewing
	private final List<Map<String,Object>> sent = new ArrayList<>();

	// send email (mock) — logs and stores to list
	public void sendEmail(String to, String subject, String body) {
		Map<String,Object> m = Map.of(
				"to", to,
				"subject", subject,
				"body", body,
				"sentAt", OffsetDateTime.now().toString()
				);
		sent.add(m);
		System.out.println("[MOCK EMAIL] to=" + to + " subject=" + subject + " body=" + body);
		// placeholder for real SMTP: if configured, integrate JavaMailSender here
	}

	public List<Map<String,Object>> getSentEmails() { return List.copyOf(sent); }
}
