package com.flavourfly.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AuthResponse {
  private String token;
  private UserInfo user;

  @Getter @Setter @AllArgsConstructor @NoArgsConstructor
  public static class UserInfo {
    private Long id; private String name; private String email; private String role;
  }
}
