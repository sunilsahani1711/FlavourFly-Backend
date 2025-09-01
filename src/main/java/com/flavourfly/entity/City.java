package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
public class City {
  @Id @GeneratedValue private Long id;
  @Column(unique = true, nullable = false) private String name;
}
