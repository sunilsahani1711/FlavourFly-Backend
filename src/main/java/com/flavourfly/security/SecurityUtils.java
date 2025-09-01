package com.flavourfly.security;

import com.flavourfly.entity.User;
import com.flavourfly.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityUtils {
	private final JwtService jwtService;
	private final UserRepository users;

	public SecurityUtils(JwtService jwtService, UserRepository users) {
		this.jwtService = jwtService;
		this.users = users;
	}

	public UserDetails loadUserFromToken(String token) {
		try {
			Jws<Claims> jws = jwtService.parse(token);
			String email = jws.getBody().getSubject();

			// ✅ Direct role claim
			String role = jws.getBody().get("role", String.class);

			List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_" + role));
			User u = users.findByEmail(email).orElse(null);
			if (u == null)
				return null;

			return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), auth);
		} catch (Exception e) {
			return null;
		}
	}
}
