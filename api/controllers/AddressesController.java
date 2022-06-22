package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.requests.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.business.responses.addresses.ReadAddressResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping(name="/api/addresses")
public class AddressesController  {

	@Autowired
	AddressService addressService;
	
	@PostMapping("/add")
	public Result add(CreateAddressRequest createAddressRequest) {
		return this.add(createAddressRequest);
	}
	
	@PostMapping("/update")
	public Result update(UpdateAddressRequest updateAddressRequest) {
		return this.update(updateAddressRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		return this.delete(deleteAddressRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<GetAllAddressesResponse>> getAll(){
		return this.addressService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<ReadAddressResponse> getById(int id){
		return this.addressService.getById(id);
	}
}
