package com.flavourfly.dto;

import lombok.Data;

@Data
public class DeliveryDetails {
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private String addressLine1;
	private String city;
	private String state;
	private String zip;
	private String addressType;
}
