package com.kodlamaio.rentACar.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceWithIndividualCustomerDto {

	private int id;
	private int customerId;
	private String firstName;
	private String lastName;
	private String invoiceNumber;
	private String carPlate;
	private double totalPrice;
}
