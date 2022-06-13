package com.kodlamaio.rentACar.business.requests.additionalItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalItemRequest {
	private int id;
	private String name;
	private String description;
	private double additionalPrice;
}
