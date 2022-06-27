package com.kodlamaio.rentACar.business.requests.cars;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
private int id;
	
	@NotBlank
	@NotEmpty
	@NotNull
	@Size(min = 3, max = 50,message="must be at least three characters.")
	private String description;
	
	@Min(0)
	@NotNull
	private double dailyPrice;
	
	
	@NotNull
	private String carPlate;
	
	@Min(0)
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