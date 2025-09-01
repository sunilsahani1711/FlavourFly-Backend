package com.flavourfly.service;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flavourfly.dto.AuthResponse;
import com.flavourfly.dto.SignInRequest;
import com.flavourfly.dto.SignUpRequest;
import com.flavourfly.entity.Role;
import com.flavourfly.entity.User;
import com.flavourfly.exception.EmailAlreadyExistsException;
import com.flavourfly.exception.InvalidCredentialsException;
import com.flavourfly.repository.UserRepository;
import com.flavourfly.security.JwtService;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
	private final UserRepository users;
	private final PasswordEncoder encoder;
	private final JwtService jwt;

	public UserService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
		this.users = users;
		this.encoder = encoder;
		this.jwt = jwt;
	}

	// ✅ Register new user
	public AuthResponse register(SignUpRequest req) {
		if (users.findByEmail(req.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		var u = new User();
		u.setName(req.getName());
		u.setEmail(req.getEmail());
		u.setPassword(encoder.encode(req.getPassword()));
		u.setRole(req.getRole() == null ? Role.CUSTOMER : Role.valueOf(req.getRole().toUpperCase()));
		users.save(u);
		return build(u);
	}

	// ✅ Authenticate existing user
	public AuthResponse authenticate(SignInRequest req) {
		var u = users.findByEmail(req.getEmail())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

		if (!encoder.matches(req.getPassword(), u.getPassword())) {
			throw new InvalidCredentialsException("Invalid credentials");
		}

		return build(u);
	}

	// ✅ Build AuthResponse with proper JWT token
	private AuthResponse build(User u) {
		var info = new AuthResponse.UserInfo(u.getId(), u.getName(), u.getEmail(), u.getRole().name());

		// ✅ Top-level "role" claim (not nested in "user")
		String token = jwt.generate(u.getEmail(), Map.of("id", String.valueOf(u.getId()), "name", u.getName(), "email",
				u.getEmail(), "role", u.getRole().name()));

		return new AuthResponse(token, info);
	}

	// ✅ Required for Spring Security
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
		var u = users.findByEmail(username).orElseThrow();
		return org.springframework.security.core.userdetails.User.withUsername(u.getEmail()).password(u.getPassword())
				.roles(u.getRole().name()).build();
	}
}
