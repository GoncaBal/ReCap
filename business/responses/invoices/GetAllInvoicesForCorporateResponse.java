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
public class GetAllInvoicesForCorporateResponse {
	private int id;
	private int rentalId;
	private String invoiceNumber;
	private Date invoiceDate;
	private int orderedAdditionalId;
	private String carPlate;
	private double totalPrice;
	private int state;
	private String companyName;
	private String email;
	private Date returnDate;
	private Date pickUpDate;
	private List<AdditionalItem> additionalItems;
}
