package com.flavourfly.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final SecurityUtils securityUtils;

	public JwtAuthFilter(SecurityUtils securityUtils) {
		this.securityUtils = securityUtils;
	}

	// ✅ Skip filter for public endpoints
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.equals("/user/signup") || path.equals("/user/signin");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String auth = req.getHeader(HttpHeaders.AUTHORIZATION);

		if (auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			UserDetails details = securityUtils.loadUserFromToken(token);
			if (details != null) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details,
						null, details.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(req, res);
	}
}
