package com.flavourfly.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final Key key;
	private final long expiration;

	public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration}") long expiration) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expiration = expiration;
	}

	public String generate(String subject, Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public Jws<Claims> parse(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}

	public String generatePasswordResetToken(String email) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + 15 * 60 * 1000); // 15 minutes

		return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(exp)
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public String validatePasswordResetToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return claims.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

}
