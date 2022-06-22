package com.kodlamaio.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ReadInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Additional;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Brand;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {

	@Autowired
	ModelMapperService modelMapperService;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	AdditionalItemRepository additionalItemRepository;
	@Autowired
	AdditionalRepository additionalRepository;
	@Autowired
	RentalRepository rentalRepository;
	

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		checkIfInvoiceExistByInvoiceNumber(createInvoiceRequest.getInvoiceNumber());
		checkIfRentalExistByRentalId(createInvoiceRequest.getRentalId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setState(true);
		invoice.setTotalPrice(calculateTotalPrice(createInvoiceRequest.getRentalId()));
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.ADDED");
	}

	

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		Invoice invoice=this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		invoice.setState(false);
		return new SuccessResult("INVOICE.CANCELLED");
	}

	@Override
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		List<GetAllInvoicesResponse> response = invoices.stream().map(
				invoice -> this.modelMapperService.forResponse().map(invoice, GetAllInvoicesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllInvoicesResponse>>(response);
	}

	@Override
	public DataResult<ReadInvoiceResponse> getById(int id) {
		Invoice invoice = this.invoiceRepository.getById(id);
		ReadInvoiceResponse response = this.modelMapperService.forResponse().map(invoice,
				ReadInvoiceResponse.class);
		return new SuccessDataResult<ReadInvoiceResponse>(response);
	}



	@Override
	public DataResult<List<AdditionalItem>> getAllAdditionalItem(int rentalId) {
		List<Additional> additionals=this.additionalRepository.getByRentalId(rentalId);
		List<AdditionalItem> additionalItems = new ArrayList<AdditionalItem>();
		
		for (Additional item : additionals) {
			AdditionalItem additionalItem=this.additionalItemRepository.getById(item.getAdditionalItem().getId());
			additionalItems.add(additionalItem);
		}
		
		return new SuccessDataResult<List<AdditionalItem>>(additionalItems);
	}
	private double calculateTotalPrice(int rentalId) {
		Rental rental = this.rentalRepository.getById(rentalId);
		double totalPrice = rental.getTotalPrice() + calculateAdditionalItemTotalPrice(rentalId);
		return totalPrice;
	}
	private double calculateAdditionalItemTotalPrice(int id) {
		double totalAdditionalService = 0;
		List<Additional> additional = this.additionalRepository.getByRentalId(id);
		for (Additional item : additional) {
			totalAdditionalService += item.getTotalPrice();
		}
		return totalAdditionalService;
	}
	
	private void checkIfInvoiceExistByInvoiceNumber(String name) {
		Invoice currentInvoice=this.invoiceRepository.getByInvoiceNumber(name);
		if (currentInvoice!=null) {
			throw new BusinessException("INVOICE.EXIST");
		}
	}
	
	private void checkIfRentalExistByRentalId(int id) {
		Rental currentRental=this.rentalRepository.getById(id);
		if (currentRental!=null) {
			throw new BusinessException("INVOICE.EXIST:REGISTERED.RENTAL.ID");
		}
	}
}
