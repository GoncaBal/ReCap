package com.kodlamaio.rentACar.business.responses.additionalItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadAdditionalItemsResponse {
	private int id;

	private String name;

	private double additionalPrice;

}