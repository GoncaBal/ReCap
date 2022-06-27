package com.kodlamaio.rentACar.business.requests.orderedAdditionalItems;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalItemsRequest {
	@NotNull
	private int additionalItemId;
	@NotNull
	private int rentalId;
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
}
