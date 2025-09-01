package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter
public class Product {
  @Id @GeneratedValue private Long id;
  private String name;
  @Column(length = 2000) private String description;
  private String imageUrl;
  private BigDecimal priceOrg;
  private BigDecimal priceMrp;
  private Integer priceOff;
  @CreationTimestamp private Instant createdAt;
  @UpdateTimestamp private Instant updatedAt;

  @ManyToMany
  @JoinTable(name = "product_category",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "product_ingredient",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private Set<Ingredient> ingredients = new HashSet<>();
}
