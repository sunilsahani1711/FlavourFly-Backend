package com.flavourfly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.flavourfly.repository.UserRepository;
import com.flavourfly.security.JwtAuthFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final UserRepository userRepository;

	public SecurityConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		return username -> {
			var u = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
			return User.withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole().name()).build();
		};
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(uds);
		provider.setPasswordEncoder(encoder);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// ✅ Do NOT prefix with /api (context-path handles that already)
						.requestMatchers("/user/signup", "/user/signin", "/user/password/reset-link",
								"/user/password/reset", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
						.permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
