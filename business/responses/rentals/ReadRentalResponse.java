package com.kodlamaio.rentACar.business.responses.rentals;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadRentalResponse {
	private int id;
	private Date pickUpDate;
	private Date returnDate;
	private int totalDays;
	private double totalPrice;
	private int carId;
	private int pickUpCityId;
	private int returnCityId;
	private int additionalItemId;
	
}