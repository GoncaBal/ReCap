package com.kodlamaio.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemsService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
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
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItems;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {

	private ModelMapperService modelMapperService;
	private InvoiceRepository invoiceRepository;
	private AdditionalItemService additionalItemService;
	private OrderedAdditionalItemsService orderedAdditionalItemsService;
	private RentalService rentalService;

	@Autowired
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceRepository invoiceRepository,
			AdditionalItemService additionalItemService, OrderedAdditionalItemsService orderedAdditionalItemsService,
			RentalService rentalService) {
		this.modelMapperService = modelMapperService;
		this.invoiceRepository = invoiceRepository;
		this.additionalItemService = additionalItemService;
		this.orderedAdditionalItemsService = orderedAdditionalItemsService;
		this.rentalService = rentalService;
	}

	@Override
	public Result addForIndividualCustomer(CreateInvoiceRequest createInvoiceRequest) {

		checkIfInvoiceExistByInvoiceNumber(createInvoiceRequest.getInvoiceNumber());
		checkIfExistRentalId(createInvoiceRequest.getRentalId());
		checkIfInvoiceNumberControl(createInvoiceRequest.getInvoiceId());

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		calculateTotalPrice(createInvoiceRequest.getRentalId(), invoice);
		invoice.setState(0);
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.ADDED");
	}

	@Override
	public Result addForCorporateCustomer(CreateInvoiceRequest createInvoiceRequest) {

		checkIfInvoiceExistByInvoiceNumber(createInvoiceRequest.getInvoiceNumber());
		checkIfExistRentalId(createInvoiceRequest.getRentalId());
		checkIfInvoiceNumberControl(createInvoiceRequest.getInvoiceId());

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		calculateTotalPrice(createInvoiceRequest.getRentalId(), invoice);
		invoice.setState(0);
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.ADDED");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {

		checkIfExistInioviceId(deleteInvoiceRequest.getId());

		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		invoice.setState(1);
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.CANCELLED");
	}

	@Override
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		List<GetAllInvoicesResponse> response = invoices.stream()
				.map(invoice -> this.modelMapperService.forResponse().map(invoice, GetAllInvoicesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllInvoicesResponse>>(response);
	}

	@Override
	public DataResult<ReadInvoiceResponse> getById(int id) {
		checkIfExistInioviceId(id);
		Invoice invoice = this.invoiceRepository.findById(id);
		ReadInvoiceResponse response = this.modelMapperService.forResponse().map(invoice, ReadInvoiceResponse.class);
		return new SuccessDataResult<ReadInvoiceResponse>(response);
	}

	@Override
	public DataResult<List<AdditionalItem>> getAllAdditionalItem(int rentalId) {
		List<OrderedAdditionalItems> additionals = this.orderedAdditionalItemsService.getByRentalId(rentalId);
		List<AdditionalItem> additionalItems = new ArrayList<AdditionalItem>();

		for (OrderedAdditionalItems item : additionals) {
			AdditionalItem additionalItem = this.additionalItemService
					.getAdditionalItemById(item.getAdditionalItem().getId());
			additionalItems.add(additionalItem);
		}
		return new SuccessDataResult<List<AdditionalItem>>(additionalItems);
	}

	private void calculateTotalPrice(int rentalId, Invoice invoice) {
		double totalPrice = 0;
		Rental rental = this.rentalService.getRentalById(rentalId);
		totalPrice = rental.getTotalPrice() + calculateAdditionalItemTotalPrice(rentalId);
		invoice.setTotalPrice(totalPrice);
	}

	private double calculateAdditionalItemTotalPrice(int id) {
		double totalAdditionalService = 0;
		List<OrderedAdditionalItems> additional = this.orderedAdditionalItemsService.getByRentalId(id);
		for (OrderedAdditionalItems item : additional) {
			totalAdditionalService += item.getTotalPrice();
		}
		return totalAdditionalService;
	}

	private void checkIfInvoiceExistByInvoiceNumber(String name) {
		Invoice currentInvoice = this.invoiceRepository.findByInvoiceNumber(name);
		if (currentInvoice != null) {
			throw new BusinessException("INVOICE.EXIST");
		}
	}

	private void checkIfInvoiceNumberControl(int invoiceId) {
		Invoice currentInvoice = this.invoiceRepository.findByRentalId(invoiceId);
		if ((currentInvoice != null) && (currentInvoice.getState() != 1)) {
			throw new BusinessException("EXIST.INVOICE.FOR.RENTAL");
		}

	}

	private void checkIfExistRentalId(int rentalId) {

		if (!rentalService.getById(rentalId).isSuccess()) {
			throw new BusinessException("INVALID.RENTAL.ID");
		}
	}

	private void checkIfExistInioviceId(int invoiceId) {
		Invoice currentInvoice = this.invoiceRepository.findById(invoiceId);
		if (currentInvoice == null) {
			throw new BusinessException("INVALID.INVOICE.ID");
		}
	}

	
}