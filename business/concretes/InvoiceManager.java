package com.kodlamaio.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ReadInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.OrderedAdditionalItemsRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItems;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {

	private ModelMapperService modelMapperService;
	private InvoiceRepository invoiceRepository;
	private AdditionalItemRepository additionalItemRepository;
	private OrderedAdditionalItemsRepository orderedAdditionalItemsRepository;
	private RentalRepository rentalRepository;
	
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceRepository invoiceRepository,
			AdditionalItemRepository additionalItemRepository,
			OrderedAdditionalItemsRepository orderedAdditionalItemsRepository, RentalRepository rentalRepository) {
		this.modelMapperService = modelMapperService;
		this.invoiceRepository = invoiceRepository;
		this.additionalItemRepository = additionalItemRepository;
		this.orderedAdditionalItemsRepository = orderedAdditionalItemsRepository;
		this.rentalRepository = rentalRepository;
	}
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		checkIfInvoiceExistByInvoiceNumber(createInvoiceRequest.getInvoiceNumber());
		checkIfRentalExistByRentalId(createInvoiceRequest.getRentalId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setState(1);
		calculateTotalPrice(createInvoiceRequest.getRentalId(),invoice);
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.ADDED");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		Invoice invoice=this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		invoice.setState(2);
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
		Invoice invoice = this.invoiceRepository.findById(id);
		ReadInvoiceResponse response = this.modelMapperService.forResponse().map(invoice,
				ReadInvoiceResponse.class);
		return new SuccessDataResult<ReadInvoiceResponse>(response);
	}

	@Override
	public DataResult<List<AdditionalItem>> getAllAdditionalItem(int rentalId) {
		List<OrderedAdditionalItems> additionals=this.orderedAdditionalItemsRepository.findByRentalId(rentalId);
		List<AdditionalItem> additionalItems = new ArrayList<AdditionalItem>();
		
		for (OrderedAdditionalItems item : additionals) {
			AdditionalItem additionalItem=this.additionalItemRepository.findById(item.getAdditionalItem().getId());
			additionalItems.add(additionalItem);
		}
		return new SuccessDataResult<List<AdditionalItem>>(additionalItems);
	}
	
	private void calculateTotalPrice(int rentalId,Invoice invoice) {
		double totalPrice=0;
		Rental rental=this.rentalRepository.findById(rentalId);
		totalPrice = rental.getTotalPrice() + calculateAdditionalItemTotalPrice(rental.getId());
		invoice.setTotalPrice(totalPrice);
	}
	
	private double calculateAdditionalItemTotalPrice(int id) {
		double totalAdditionalService = 0;
		List<OrderedAdditionalItems> additional = this.orderedAdditionalItemsRepository.findByRentalId(id);
		for (OrderedAdditionalItems item : additional) {
			totalAdditionalService += item.getTotalPrice();
		}
		return totalAdditionalService;
	}
	
	private void checkIfInvoiceExistByInvoiceNumber(String name) {
		Invoice currentInvoice=this.invoiceRepository.findByInvoiceNumber(name);
		if (currentInvoice!=null) {
			throw new BusinessException("INVOICE.EXIST");
		}
	} 
	
	private void checkIfRentalExistByRentalId(int id) {
		Rental currentRental=this.rentalRepository.findById(id);
		if (currentRental==null) {
			throw new BusinessException("INVOICE.EXIST:REGISTERED.RENTAL.ID");
		}
	}
}
