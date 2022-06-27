package com.kodlamaio.rentACar.business.requests.corporateCustomers;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {
	@NotNull
	private int corporateId;
}
