package com.kodlamaio.rentACar.business.responses.cars;

import com.kodlamaio.rentACar.entities.concretes.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsResponse {
private int id;
private String description;
private double dailyPrice;
private String carPlate;
private double kilometer;
private int brandId;
private int colorId;
private int state;
public GetAllCarsResponse(Car entity) {
	this.id = entity.getId();
	this.description = entity.getDescription();
	this.dailyPrice = entity.getDailyPrice();
	this.carPlate = entity.getCarPlate();
	this.kilometer = entity.getKilometer();
	this.brandId = entity.getBrand().getId();
	this.colorId = entity.getColor().getId();
	this.state = entity.getState();
}


}
