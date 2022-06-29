package com.kodlamaio.rentACar.business.responses.invoices;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllInvoicesResponse {
	private int id;
	private int rentalId;
	private String invoiceNumber;
	private Date invoiceDate;
	private int orderedAdditionalId;
	private String carPlate;
	private double totalPrice;
	private int state; 
	private int customerId;
	private String email;
	private Date returnDate;
	private Date pickUpDate;
	
}
