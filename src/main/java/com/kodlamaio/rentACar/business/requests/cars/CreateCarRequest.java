package com.kodlamaio.rentACar.business.requests.cars;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
	@Size(min=2,max=50)
	private String description;
	
	@Min(20)
	@NotEmpty
	
	private double dailyPrice;
	private String carPlate;
	private double kilometer;
	private int brandId;
	private int colorId;
	private int state;
	
}
