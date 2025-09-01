package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","product_id"}))
public class Favourite {
  @Id @GeneratedValue private Long id;
  @ManyToOne(optional = false) private User user;
  @ManyToOne(optional = false) private Product product;
}
