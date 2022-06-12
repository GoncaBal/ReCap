package com.kodlamaio.rentACar.business.requests.additionalItems;

import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalItemRequest {
	private int id;

	private String name;

	private double additionalPrice;
}
