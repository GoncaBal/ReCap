package com.kodlamaio.rentACar.business.responses.invoices;

import java.util.Date;
import java.util.List;

import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReadInvoiceResponse {
	private int id;
	private int rentalId;
	private String invoiceNumber;
	private Date invoiceDate;
	private int additionalId;
	private String carPlate;
	private double totalPrice;
	private int state;
	private String firstName;
	private Date returnDate;
	private Date pickUpDate;
	private List<AdditionalItem> additionalItems;

}
