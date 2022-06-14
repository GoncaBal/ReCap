package com.kodlamaio.rentACar.business.responses.customers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCustomerResponse {
	private int id;

	private String firstName;

	private String lastName;

	private String nationalIdentification;

	private int birthYear;
	
	private String eMail;

	private String password;
}
