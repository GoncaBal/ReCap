package com.kodlamaio.rentACar.business.responses.maintenances;

import java.time.LocalDate;

import com.kodlamaio.rentACar.entities.concretes.Maintenance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllMaintenancesResponse {
private int id;
private LocalDate dateSent;
private LocalDate dateReturned;
private int carId;
private int state;
public GetAllMaintenancesResponse(Maintenance entity) {
	this.id = entity.getId();
	this.dateSent = entity.getDateSent();
	this.dateReturned = entity.getDateReturned();
	this.carId = entity.getCar().getId();
}

}
