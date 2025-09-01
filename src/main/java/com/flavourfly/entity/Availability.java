package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id","outlet_id"}))
public class Availability {
  @Id @GeneratedValue private Long id;
  @ManyToOne(optional = false) private Product product;
  @ManyToOne(optional = false) private Outlet outlet;
}
