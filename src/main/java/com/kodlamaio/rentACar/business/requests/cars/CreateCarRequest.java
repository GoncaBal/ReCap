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
	
	private int id;
	
	@NotBlank
	@NotEmpty
	@NotNull
	@Size(min=2,max=50)
	private String description;
	
	@Min(20)
	@NotEmpty
	@NotNull
	@NotBlank
	private double dailyPrice;
	
	@NotEmpty
	@NotNull
	@NotBlank
	@Pattern(regexp="/^([0-9]{2})([A-Z]{1,3})([0-9]{2,4})$/")
	private String carPlate;
	
	@NotEmpty
	@NotNull
	@NotBlank
	private double kilometer;

	@NotEmpty
	@NotNull
	@NotBlank
	private int brandId;
	@NotEmpty
	@NotNull
	@NotBlank
	private int colorId;
	private int state;
	
}
