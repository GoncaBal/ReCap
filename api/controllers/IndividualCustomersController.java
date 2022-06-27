package com.kodlamaio.rentACar.api.controllers;

import java.rmi.RemoteException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.requests.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individualCustomers.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.responses.individualCustomers.ReadIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualcustomers")
public class IndividualCustomersController {
	
	private IndividualCustomerService individualCustomerService;
	
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
	
		this.individualCustomerService = individualCustomerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll() {
		return this.individualCustomerService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadIndividualCustomerResponse> getById(@RequestParam int id) {
		return this.individualCustomerService.getById(id);
	}
	@GetMapping("/getallbypage")
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll(@RequestParam int pageNumber, int pageSize){
		return this.individualCustomerService.getAll(pageNumber,pageSize);
	}
}
