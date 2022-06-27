package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.requests.addresses.CreateDifferentAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.CreateSameAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateDifferentAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateSameAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.business.responses.addresses.ReadAddressResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/addresses")
public class AddressesController {

	private AddressService addressService;

	public AddressesController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}

	@PostMapping("/addsameaddressforindividualcustomer")
	public Result addSameAddressForIndividualCustomer(
			@RequestBody @Valid CreateSameAddressRequest createAddressRequest) {
		return this.addressService.addSameAddressForIndividualCustomer(createAddressRequest);
	}

	@PostMapping("/addsameaddressforcorporatecustomer")
	public Result addSameAddressForCorporateCustomer(
			@RequestBody @Valid CreateSameAddressRequest createAddressRequest) {
		return this.addressService.addSameAddressForCorporateCustomer(createAddressRequest);
	}

	@PostMapping("/adddifferentaddressforindividualcustomer")
	public Result addDifferentAddressForIndividualCustomer(
			@RequestBody @Valid CreateDifferentAddressRequest createDifferentAddressRequest) {
		return this.addressService.addDifferentAddressForIndividualCustomer(createDifferentAddressRequest);
	}

	@PostMapping("/adddifferentaddressforcorporatecustomer")
	public Result addDifferentAddressForCorporateCustomer(
			@RequestBody @Valid CreateDifferentAddressRequest createDifferentAddressRequest) {
		return this.addressService.addDifferentAddressForCorporateCustomer(createDifferentAddressRequest);
	}

	@PostMapping("/updatesameaddressforindividualcustomer")
	public Result updateSameAddressForIndividualCustomer(
			@RequestBody @Valid UpdateSameAddressRequest updateAddressRequest) {
		return this.addressService.updateSameAddressForIndividualCustomer(updateAddressRequest);
	}

	@PostMapping("/updatesameaddressforcorporatecustomer")
	public Result updateSameAddressForCorporateCustomer(
			@RequestBody @Valid UpdateSameAddressRequest updateAddressRequest) {
		return this.addressService.updateSameAddressForCorporateCustomer(updateAddressRequest);
	}

	@PostMapping("/updatedifferentaddressforindividualcustomer")
	public Result updateDifferentAddressForIndividualCustomer(
			@RequestBody @Valid UpdateDifferentAddressRequest updateDifferentAddressRequest) {
		return this.addressService.updateDifferentAddressForIndividualCustomer(updateDifferentAddressRequest);
	}

	@PostMapping("/updatedifferentaddressforcorporatecustomer")
	public Result updateDifferentAddressForCorporateCustomer(
			@RequestBody @Valid UpdateDifferentAddressRequest updateDifferentAddressRequest) {
		return this.addressService.updateDifferentAddressForCorporateCustomer(updateDifferentAddressRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAddressRequest deleteAddressRequest) {
		return this.delete(deleteAddressRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllAddressesResponse>> getAll() {
		return this.addressService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadAddressResponse> getById(@RequestParam @Valid int id) {
		return this.addressService.getById(id);
	}
}
