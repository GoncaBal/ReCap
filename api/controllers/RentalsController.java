package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	private RentalService rentalService;

	
	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@PostMapping("/addindividualcustomerforrental")
	public Result addIndividualCustomerForRental(@RequestBody @Valid CreateRentalRequest createRentalRequest) {
		return this.rentalService.addIndividualCustomerForRental(createRentalRequest);
	}

	@PostMapping("/addcorporatecustomerforrental")
	public Result addCorporateCustomerForRental(@RequestBody @Valid CreateRentalRequest createRentalRequest) {
		return this.rentalService.addCorporateCustomerForRental(createRentalRequest);
	}

	@PostMapping("/updateindividualcustomerforrental")
	public Result updateIndividualCustomerForRental(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateIndividualCustomerForRental(updateRentalRequest);
	}

	@PostMapping("/updatecorporatecustomerforrental")
	public Result updateCorporateCustomerForRental(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateCorporateCustomerForRental(updateRentalRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentalRequest deleteRentalRequest) {
		return this.rentalService.delete(deleteRentalRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllRentalsResponse>> getAll() {
		return this.rentalService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadRentalResponse> getById(@RequestParam  int id) {
		return this.rentalService.getById(id);
	}
}
