package com.flavourfly.controller;

import com.flavourfly.dto.ProductDto;
import com.flavourfly.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class ProductController {
  private final ProductService service;
  public ProductController(ProductService service) { this.service = service; }

  @GetMapping
  public ResponseEntity<List<ProductDto>> list() {
    return ResponseEntity.ok(service.list());
  }
}
