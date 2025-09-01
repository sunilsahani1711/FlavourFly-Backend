package com.flavourfly.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flavourfly.exception.EmailAlreadyExistsException;
import com.flavourfly.exception.InvalidCredentialsException;

@RestControllerAdvice
public class ApiExceptionHandler {

	// 1️⃣ Handle signup email conflict
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}

	// 2️⃣ Handle invalid login
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
	}

	// 3️⃣ Handle validation errors (@Valid DTOs)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errors);
	}

	// 4️⃣ Handle IllegalArgument
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}

	// 5️⃣ Handle generic runtime exceptions
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}

	// 6️⃣ Handle unexpected server errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("message", "Something went wrong. Please try again later."));
	}
}
