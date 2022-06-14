package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CustomerService;
import com.kodlamaio.rentACar.business.requests.customers.CreateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.customers.DeleteCustomerRequest;
import com.kodlamaio.rentACar.business.requests.customers.UpdateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.customers.GetAllCustomersResponse;
import com.kodlamaio.rentACar.business.responses.customers.ReadCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
		return this.customerService.add(createCustomerRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
		return this.customerService.update(updateCustomerRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCustomerRequest deleteCustomerRequest) {
		return this.customerService.delete(deleteCustomerRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllCustomersResponse>> getAll() {
		return this.customerService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadCustomerResponse> getById(@RequestParam @Valid int id) {
		return this.customerService.getById(id);
	}
	@GetMapping("getallbypage")
	DataResult<List<GetAllCustomersResponse>> getAll(@RequestParam @Valid int pageNumber, int pageSize){
		return this.customerService.getAll(pageNumber,pageSize);
	}
}
