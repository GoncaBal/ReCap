package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemsService;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.CreateOrderedAdditionalItemsRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.DeleteOrderedAdditionalsItemsRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.UpdateOrderedAdditionalItemsRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.ReadOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.OrderedAdditionalItemsRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItems;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class OrderedAdditionalItemsManager implements OrderedAdditionalItemsService {

	private ModelMapperService modelMapperService;
	private OrderedAdditionalItemsRepository orderedAdditionalItemsRepository;
	private AdditionalItemRepository additionalItemRepository;
	private RentalRepository rentalRepository;

	public OrderedAdditionalItemsManager(ModelMapperService modelMapperService,
			OrderedAdditionalItemsRepository orderedAdditionalItemsRepository,
			AdditionalItemRepository additionalItemRepository, RentalRepository rentalRepository) {

		this.modelMapperService = modelMapperService;
		this.orderedAdditionalItemsRepository = orderedAdditionalItemsRepository;
		this.additionalItemRepository = additionalItemRepository;
		this.rentalRepository = rentalRepository;
	}

	@Override
	public Result add(CreateOrderedAdditionalItemsRequest createAdditionalItemRequest) {
		checkIfExistAdditionalItemId(createAdditionalItemRequest.getAdditionalItemId());
		checkIfExistRentalId(createAdditionalItemRequest.getRentalId());
		OrderedAdditionalItems orderedAdditionalItems = this.modelMapperService.forRequest()
				.map(createAdditionalItemRequest, OrderedAdditionalItems.class);
		AdditionalItem additionalItem = additionalItemRepository
				.findById(createAdditionalItemRequest.getAdditionalItemId());
		calculateTotalPrice(orderedAdditionalItems, additionalItem);
		this.orderedAdditionalItemsRepository.save(orderedAdditionalItems);
		return new SuccessResult("ADDITIONAL.ADDED");
	}

	@Override
	public Result update(UpdateOrderedAdditionalItemsRequest updateOrderedAdditionalItemRequest) {
		checkIfExistOrderedAdditionalItemsId(updateOrderedAdditionalItemRequest.getId());
		checkIfExistAdditionalItemId(updateOrderedAdditionalItemRequest.getAdditionalItemId());
		checkIfExistRentalId(updateOrderedAdditionalItemRequest.getRentalId());
		OrderedAdditionalItems updateToOrderedAdditionalItems = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalItemRequest, OrderedAdditionalItems.class);
		AdditionalItem additionalItem = additionalItemRepository
				.findById(updateOrderedAdditionalItemRequest.getAdditionalItemId());
		calculateTotalPrice(updateToOrderedAdditionalItems, additionalItem);
		this.orderedAdditionalItemsRepository.save(updateToOrderedAdditionalItems);
		return new SuccessResult("ADDITIONAL.UPDATED");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalsItemsRequest deleteAdditionalRequest) {
		checkIfExistOrderedAdditionalItemsId(deleteAdditionalRequest.getId());
		this.orderedAdditionalItemsRepository.deleteById(deleteAdditionalRequest.getId());
		return new SuccessResult("ADDITIONAL.DELETED");
	}

	@Override
	public DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll() {
		List<OrderedAdditionalItems> orderedAdditionalItems = this.orderedAdditionalItemsRepository.findAll();
		List<GetAllOrderedAdditionalItemsResponse> response = orderedAdditionalItems.stream()
				.map(orderedAdditional -> this.modelMapperService.forResponse().map(orderedAdditional,
						GetAllOrderedAdditionalItemsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllOrderedAdditionalItemsResponse>>(response);
	}

	@Override
	public DataResult<ReadOrderedAdditionalItemsResponse> getById(int id) {
		checkIfExistOrderedAdditionalItemsId(id);
		OrderedAdditionalItems orderedAdditionalItems = this.orderedAdditionalItemsRepository.findById(id);
		ReadOrderedAdditionalItemsResponse response = this.modelMapperService.forResponse().map(orderedAdditionalItems,
				ReadOrderedAdditionalItemsResponse.class);
		return new SuccessDataResult<ReadOrderedAdditionalItemsResponse>(response);
	}

	private long calculateTotalDay(OrderedAdditionalItems orderedAdditionalItems) {
		long dayDifference = (orderedAdditionalItems.getReturnDate().getTime()
				- orderedAdditionalItems.getPickUpDate().getTime());
		if (dayDifference < 0) {
			throw new BusinessException("INVALID DATE");
		}
		long time = TimeUnit.DAYS.convert(dayDifference, TimeUnit.MILLISECONDS);
		orderedAdditionalItems.setTotalDays((int) time);
		return time;
	}

	private void checkIfExistOrderedAdditionalItemsId(int id) {
		OrderedAdditionalItems currentOrderedAdditionalItems = this.orderedAdditionalItemsRepository.findById(id);
		if (currentOrderedAdditionalItems == null) {
			throw new BusinessException("INVALID.ORDERED.ADDITIONAL.ITEMS.ID");
		}
	}

	private void checkIfExistAdditionalItemId(int id) {
		AdditionalItem currentAdditionalItem = this.additionalItemRepository.findById(id);
		if (currentAdditionalItem == null) {
			throw new BusinessException("INVALID.ADDITIONAL.ITEM.ID");
		}
	}

	private void checkIfExistRentalId(int id) {
		Rental currentRental = this.rentalRepository.findById(id);
		if (currentRental == null) {
			throw new BusinessException("INVALID.RENTAL.ID");
		}
	}

	private void calculateTotalPrice(OrderedAdditionalItems orderedAdditionalItems, AdditionalItem additionalItem) {
		double totalPrice = 0;
		long time = calculateTotalDay(orderedAdditionalItems);
		totalPrice = additionalItem.getAdditionalPrice() * time;
		orderedAdditionalItems.setTotalPrice(totalPrice);
	}

}
