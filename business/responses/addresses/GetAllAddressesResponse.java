package com.kodlamaio.rentACar.business.responses.addresses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAddressesResponse {
	private int id;
	private String contactAdress;
	private String invoiceAdress;
	private int customerId;
}
