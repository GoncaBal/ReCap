package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemsService;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.CreateOrderedAdditionalItemsRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.DeleteOrderedAdditionalsItemsRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.UpdateOrderedAdditionalItemsRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.ReadOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedadditionalitems")
public class OrderedAdditionalItemsController {

	private OrderedAdditionalItemsService orderedAdditionalService;

	public OrderedAdditionalItemsController(OrderedAdditionalItemsService additionalService) {
		this.orderedAdditionalService = additionalService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalItemsRequest createOrderedAdditionalItemsRequest) {

		return this.orderedAdditionalService.add(createOrderedAdditionalItemsRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalItemsRequest updateOrderedAdditionalItemsRequest) {
		return this.orderedAdditionalService.update(updateOrderedAdditionalItemsRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalsItemsRequest deleteOrderedAdditionalsItemsRequest) {
		return this.orderedAdditionalService.delete(deleteOrderedAdditionalsItemsRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll() {
		return this.orderedAdditionalService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadOrderedAdditionalItemsResponse> getById(@RequestParam @Valid int id) {
		return this.orderedAdditionalService.getById(id);
	}
}
