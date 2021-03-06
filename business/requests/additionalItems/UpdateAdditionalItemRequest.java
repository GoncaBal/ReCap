package com.kodlamaio.rentACar.business.requests.additionalItems;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalItemRequest {
	private int id;
	@NotBlank
	@NotNull
	@Size(min=3,max=50,message="Must be at least three characters.")
	private String name;
	@NotBlank
	@NotNull
	@Size(min=3,max=150,message="Must be at least three characters.")
	private String description;
	@Min(20)
	private double additionalPrice;
}
