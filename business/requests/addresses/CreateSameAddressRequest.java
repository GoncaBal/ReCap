package com.kodlamaio.rentACar.business.requests.addresses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSameAddressRequest {
	
	@NotBlank
	@NotNull
	@Size(min=3,max=150,message="Must be at least three characters.")
	private String contactAdress;
	@NotNull
	private int customerId;
}
