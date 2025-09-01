package com.flavourfly.controller;

import com.flavourfly.dto.CartMutationRequest;
import com.flavourfly.entity.CartItem;
import com.flavourfly.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cart")
public class CartController {
  private final CartService service;
  public CartController(CartService service) { this.service = service; }

  @GetMapping public ResponseEntity<List<CartItem>> list(Authentication auth) { return ResponseEntity.ok(service.list(auth)); }
  @PostMapping public ResponseEntity<List<CartItem>> add(Authentication auth, @RequestBody CartMutationRequest req) { return ResponseEntity.ok(service.add(auth, req)); }
  @PatchMapping public ResponseEntity<List<CartItem>> remove(Authentication auth, @RequestBody CartMutationRequest req) { return ResponseEntity.ok(service.remove(auth, req)); }
}
