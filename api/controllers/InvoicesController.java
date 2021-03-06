package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ReadInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping("/addforindividualcustomer")
	public Result addForIndividualCustomer(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.addForIndividualCustomer(createInvoiceRequest);
	}
	@PostMapping("/addforcorporatecustomer")
	public Result addForCorporateCustomer(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.addForCorporateCustomer(createInvoiceRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		return this.invoiceService.getAll();
	}
	
	

	@GetMapping("/getalladditionalitem")
	public DataResult<List<AdditionalItem>> getAllAdditionalItem(@RequestParam int rentalId) {
		return this.invoiceService.getAllAdditionalItem(rentalId);
	}

	@GetMapping("/getbyid")
	public DataResult<ReadInvoiceResponse> getById(@RequestParam int id) {
		return this.invoiceService.getById(id);
	}

}
