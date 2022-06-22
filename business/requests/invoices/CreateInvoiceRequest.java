package com.kodlamaio.rentACar.business.requests.invoices;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	private int id;
	private String invoiceNumber;
	private Date invoiceDate;
	private int rentalId;
	

}
