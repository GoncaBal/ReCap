package com.kodlamaio.rentACar.business.requests.rentals;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
	@NotNull
	@DateTimeFormat
	private Date pickUpDate;
	@NotNull
	@DateTimeFormat
	private Date returnDate;
	@Min(0)
	private int totalDays;
	@Min(0)
	private double totalPrice;
	@NotNull
	private int carId;
	@NotNull
	private int pickUpCityId;
	@NotNull
	private int returnCityId;
	@NotNull
	private int customerId;
}
