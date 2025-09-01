package com.flavourfly.controller;

import com.flavourfly.dto.AuthResponse;
import com.flavourfly.dto.SignInRequest;
import com.flavourfly.dto.SignUpRequest;
import com.flavourfly.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  public UserController(UserService userService) { this.userService = userService; }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest req) {
    return ResponseEntity.ok(userService.register(req));
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> signin(@RequestBody SignInRequest req) {
    return ResponseEntity.ok(userService.authenticate(req));
  }
}
