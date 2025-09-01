package com.flavourfly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
public class Address {
  @Id @GeneratedValue private Long id;
  private String name;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String phone;
  private String emailAddress;
  private String type;
  @ManyToOne(optional = false) private User user;
  public void setUser(User user) {
	    this.user = user;
	}

}
