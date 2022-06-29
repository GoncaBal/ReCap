package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ReadInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

public interface InvoiceService {

	Result addForIndividualCustomer(CreateInvoiceRequest createInvoiceRequest);

	Result addForCorporateCustomer(CreateInvoiceRequest createInvoiceRequest);
	
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);

	DataResult<List<GetAllInvoicesResponse>> getAll();
	
	DataResult<ReadInvoiceResponse> getById(int id);

	DataResult<List<AdditionalItem>> getAllAdditionalItem(int rentalId);
	
}
