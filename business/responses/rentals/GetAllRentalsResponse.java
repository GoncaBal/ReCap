package com.kodlamaio.rentACar.business.responses.rentals;

import java.time.LocalDate;
import java.util.Date;

import com.kodlamaio.rentACar.entities.concretes.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRentalsResponse {
	private int id;
	private Date pickUpDate;
	private Date returnDate;
	private int totalDays;
	private double totalPrice;
	private int carId;
	private int pickUpCityId;
	private int returnCityId;
	private int additionalId;
	public GetAllRentalsResponse(Rental entity) {
		this.id = entity.getId();
		this.pickUpDate = entity.getPickUpDate();
		this.returnDate = entity.getReturnDate();
		this.totalDays = entity.getTotalDays();
		this.totalPrice = entity.getTotalPrice();
		this.carId=entity.getCar().getId();
		this.pickUpCityId=entity.getPickCity().getId();
		this.returnCityId=entity.getReturnCity().getId();
		this.additionalId=entity.getId();
	}
	

}
