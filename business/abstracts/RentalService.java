package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Rental;

public interface RentalService {

	Result addIndividualCustomerForRental(CreateRentalRequest createRentalRequest);

	Result addCorporateCustomerForRental(CreateRentalRequest createRentalRequest);

	Result updateIndividualCustomerForRental(UpdateRentalRequest updateRentalRequest);

	Result updateCorporateCustomerForRental(UpdateRentalRequest updateRentalRequest);

	Result delete(DeleteRentalRequest deleteRentalRequest);

	DataResult<List<GetAllRentalsResponse>> getAll();

	DataResult<ReadRentalResponse> getById(int id);

	Rental getRentalById(int rentalId);
}
