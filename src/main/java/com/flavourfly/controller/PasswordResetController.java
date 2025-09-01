package com.flavourfly.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flavourfly.repository.UserRepository;
import com.flavourfly.security.JwtService;

@RestController
@RequestMapping("/user/password")
public class PasswordResetController {

	private final UserRepository users;
	private final JwtService jwtService;
	private final JavaMailSender mailSender;
	private final PasswordEncoder encoder;

	public PasswordResetController(UserRepository users, JwtService jwtService, JavaMailSender mailSender,
			PasswordEncoder encoder) {
		this.users = users;
		this.jwtService = jwtService;
		this.mailSender = mailSender;
		this.encoder = encoder;
	}

	// 1️⃣ Request reset link
	@PostMapping("/reset-link")
	public ResponseEntity<?> requestLink(@RequestBody Map<String, String> body) {
		String email = body.get("email");
		var userOpt = users.findByEmail(email);
		if (userOpt.isEmpty())
			return ResponseEntity.ok(Map.of("message", "If email exists, link sent"));

		String token = jwtService.generatePasswordResetToken(email);
		String resetUrl = "http://localhost:3000/reset-password?token=" + token;

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("Password Reset");
		msg.setText("Click here to reset your password: " + resetUrl);
		mailSender.send(msg);

		return ResponseEntity.ok(Map.of("message", "If email exists, link sent"));
	}

	// 2️⃣ Reset password using token
	@PostMapping("/reset")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
		String token = body.get("token");
		String newPassword = body.get("password");

		String email = jwtService.validatePasswordResetToken(token);
		if (email == null) {
			return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired token"));
		}

		var user = users.findByEmail(email).orElseThrow();
		user.setPassword(encoder.encode(newPassword));
		users.save(user);

		return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
	}
}
