package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity @Getter @Setter
public class OrderItem {
  @Id @GeneratedValue private Long id;
  @ManyToOne(optional = false) private Order order;
  @ManyToOne(optional = false) private Product product;
  private int quantity;
  private BigDecimal unitPrice;
  private BigDecimal lineTotal;
}
