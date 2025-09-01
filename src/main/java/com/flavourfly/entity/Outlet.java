package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
public class Outlet {
  @Id @GeneratedValue private Long id;
  private String name;
  private String address;
  private String phone;
  private String timing;
  @ManyToOne(optional = false) private City city;
}
