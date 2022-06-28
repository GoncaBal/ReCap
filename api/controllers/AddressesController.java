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

	@PostMapping("/addsameaddress")
	public Result addSameAddress(@RequestBody @Valid CreateSameAddressRequest createAddressRequest) {
		return this.addressService.addSameAddress(createAddressRequest);
	}

	@PostMapping("/adddifferentaddress")
	public Result addDifferentAddress(@RequestBody @Valid CreateDifferentAddressRequest createDifferentAddressRequest) {
		return this.addressService.addDifferentAddress(createDifferentAddressRequest);
	}

	@PostMapping("/updatesameaddress")
	public Result updateSameAddress(@RequestBody @Valid UpdateSameAddressRequest updateAddressRequest) {
		return this.addressService.updateSameAddress(updateAddressRequest);
	}

	@PostMapping("/updatedifferentaddress")
	public Result updateDifferentAddress(
			@RequestBody @Valid UpdateDifferentAddressRequest updateDifferentAddressRequest) {
		return this.addressService.updateDifferentAddress(updateDifferentAddressRequest);
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
