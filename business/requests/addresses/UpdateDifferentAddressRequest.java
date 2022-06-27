package com.kodlamaio.rentACar.business.requests.addresses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDifferentAddressRequest {
	private int id;
	@NotBlank
	@NotNull
	@Size(min=3,max=50,message="Must be at least three characters.")
	private String contactAdress;
	@NotBlank
	@NotNull
	@Size(min=3,max=50,message="Must be at least three characters.")
	private String invoiceAdress;
	@NotNull
	private int customerId;
}
