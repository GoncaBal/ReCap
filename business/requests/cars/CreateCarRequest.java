package com.kodlamaio.rentACar.business.requests.cars;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
	@NotBlank
	@NotEmpty
	@NotNull
	@Size(min = 3, max = 50,message="must be at least three characters.")
	private String description;
	
	@Min(20)
	@NotNull
	private double dailyPrice;
	
	//@Pattern(regexp = "/^([0-9]{2})([A-Z]{1,3})([0-9]{2,4})$/")
	@NotNull
	private String carPlate;
	
	@NotNull
	private double kilometer;
	
	@NotNull
	private int brandId;

	@NotNull
	private int colorId;
	
	@Min(500)
	@NotNull
	private int minFindeksScore;

}
