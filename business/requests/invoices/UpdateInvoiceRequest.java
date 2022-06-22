package com.kodlamaio.rentACar.business.requests.invoices;

import java.util.Date;
import java.util.List;

import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	private int id;
	private String invoiceNumber;
	private Date invoiceDate;
	private int rentalId;

}
