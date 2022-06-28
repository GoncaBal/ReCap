package com.kodlamaio.rentACar.business.requests.maintenances;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceRequest {
	private int maintenanceId;
	@NotNull 
	@DateTimeFormat
	private Date dateSent;
	@NotNull
	@DateTimeFormat
	private Date dateReturned;
	@NotNull
	private int carId;
}
