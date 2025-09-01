package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "orders")
public class Order {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional = false) private User user;
  @Enumerated(EnumType.STRING) private OrderStatus status = OrderStatus.PENDING;
  private Instant placedAt = Instant.now();
  @Enumerated(EnumType.STRING) private PaymentMethod paymentMethod;
  private String deliveryName; private String deliveryStreet; private String deliveryCity; private String deliveryState; private String deliveryZip; private String deliveryPhone; private String deliveryEmail;
  private BigDecimal totalAmount;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items = new ArrayList<>();
}
