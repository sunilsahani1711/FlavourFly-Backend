package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
public class Ingredient {
  @Id @GeneratedValue private Long id;
  @Column(unique = true, nullable = false) private String name;
}
