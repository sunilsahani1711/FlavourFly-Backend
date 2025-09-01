package com.flavourfly.controller;

import com.flavourfly.entity.Address;
import com.flavourfly.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/address")
public class AddressController {
  private final AddressService service;
  public AddressController(AddressService service) { this.service = service; }

  @GetMapping public ResponseEntity<List<Address>> list(Authentication a) { return ResponseEntity.ok(service.list(a)); }
  @PostMapping public ResponseEntity<Address> save(Authentication a, @RequestBody Address addr) { return ResponseEntity.ok(service.save(a, addr)); }
  @DeleteMapping("/{id}") public ResponseEntity<Void> del(Authentication a, @PathVariable Long id) { service.delete(a, id); return ResponseEntity.noContent().build(); }
}
