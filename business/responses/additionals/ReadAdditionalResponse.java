package com.kodlamaio.rentACar.business.responses.additionals;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadAdditionalResponse {
	private int id;
	private int additionalItemId;
	private int rentalId;
	private Date pickUpDate;
	private Date returnDate;
	private int totalDays;
	private double totalPrice;
}
