package com.kodlamaio.rentACar.business.responses.corporateCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCorporateCustomerResponse {
	private int corporateId;
	private int taxNumber;
	private String companyName;
	private String customerNumber;
	private String phoneNumber;
	private String email;
	private String password;
}
