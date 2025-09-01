package com.flavourfly.dto;

import lombok.Data;

@Data
public class SignUpRequest {
  private String name;
  private String email;
  private String password;
  private String role; // ADMIN/VENDOR/CUSTOMER
}
