package com.kodlamaio.rentACar.business.requests.invoices;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	private int id;
	@NotBlank
	@NotNull
	@Size(min=4,max=10,message="Must be at least four characters.")
	private String invoiceNumber;
	@NotNull
	@DateTimeFormat
	private Date invoiceDate;
	@NotNull
	private int rentalId;

}
