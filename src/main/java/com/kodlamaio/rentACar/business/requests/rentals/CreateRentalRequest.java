package com.kodlamaio.rentACar.business.requests.rentals;

import java.util.Date;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
	private int id;
	private Date pickUpDate;
	private Date returnDate;
	@Min(0)
	private int totalDays;
	@Min(0)
	private double totalPrice;
	private int carId;
	private int pickUpCityId;
	private int returnCityId;
	private int additionalId;
}
