package com.kodlamaio.rentACar.business.responses.individualCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllIndividualCustomersResponse {

	private String customerNumber;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private String nationalIdentification;
	private int birthYear;
	private String email;
	private String password;
}
