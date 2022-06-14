package com.kodlamaio.rentACar.business.responses.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAdditionalsResponse {
	private int id;
	private int additionalItemId;
	private int rentalId;
}
