package com.kodlamaio.rentACar.business.responses.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReadCarResponse {
	private int id;
	private String description;
	private double dailyPrice;
	private String carPlate;
	private double kilometer;
	private int brandId;
	private int colorId;
	private int state;
}
