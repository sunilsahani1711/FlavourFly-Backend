package com.flavourfly.controller;

import com.flavourfly.dto.PlaceOrderRequest;
import com.flavourfly.entity.Order;
import com.flavourfly.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderController {
  private final OrderService service;
  public OrderController(OrderService service) { this.service = service; }

  @GetMapping public ResponseEntity<List<Order>> list(Authentication a) { return ResponseEntity.ok(service.list(a)); }
  @PostMapping public ResponseEntity<Order> place(Authentication a, @RequestBody PlaceOrderRequest req) { return ResponseEntity.ok(service.place(a, req)); }
}
