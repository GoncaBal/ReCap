package com.kodlamaio.rentACar.business.requests.colors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateColorRequest {
	private int id;
	@NotBlank
	@NotNull
	@Size(min=3,max=50,message="Must be at least three characters.")
	private String name;
}
