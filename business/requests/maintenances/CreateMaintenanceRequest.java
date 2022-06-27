package com.kodlamaio.rentACar.business.requests.maintenances;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaintenanceRequest {
	@NotNull
	@DateTimeFormat
	private Date dateSent;
	@NotNull
	@DateTimeFormat
	private Date dateReturned;
	@NotNull
	private int carId;
}
