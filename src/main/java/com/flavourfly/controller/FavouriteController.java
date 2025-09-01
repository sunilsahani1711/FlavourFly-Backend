package com.flavourfly.controller;

import com.flavourfly.entity.Favourite;
import com.flavourfly.service.FavouriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/favorite")
public class FavouriteController {
  private final FavouriteService service;
  public FavouriteController(FavouriteService service) { this.service = service; }

  @GetMapping public ResponseEntity<List<Favourite>> list(Authentication a) { return ResponseEntity.ok(service.list(a)); }
  @PostMapping public ResponseEntity<List<Favourite>> add(Authentication a, @RequestBody java.util.Map<String,String> body) { return ResponseEntity.ok(service.add(a, Long.parseLong(body.get("productId")))); }
  @PatchMapping public ResponseEntity<List<Favourite>> remove(Authentication a, @RequestBody java.util.Map<String,String> body) { return ResponseEntity.ok(service.remove(a, Long.parseLong(body.get("productId")))); }
}
