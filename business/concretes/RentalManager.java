package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Additional;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	RentalRepository rentalRepository;
	CarRepository carRepository;
	ModelMapperService modelMapperService;
	AdditionalRepository additionalRepository;
	
	@Autowired
	public RentalManager(RentalRepository rentalRepository, ModelMapperService modelMapperService,
			CarRepository carRepository, AdditionalRepository additionalRepository) {
		this.rentalRepository = rentalRepository;
		this.modelMapperService = modelMapperService;
		this.carRepository = carRepository;
		this.additionalRepository = additionalRepository;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {

		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		Car car = this.carRepository.getById(createRentalRequest.getCarId());
	//	AdditionalItem additional = this.additionalRepository.getById(createRentalRequest.getAdditionalId());
		long time = calculateTotalDay(rental);
		double totalPrice = car.getDailyPrice() * time;
		if ((rental.getPickCity().getId()) != (rental.getReturnCity().getId())) {
			totalPrice = totalPrice + 750;
		}
//		totalPrice = totalPrice + (additional.getAdditionalPrice());
		car.setState(3);

		rental.setTotalPrice(totalPrice);
		rental.setCar(car);

		this.rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rentalToUpdate = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car car = this.carRepository.getById(updateRentalRequest.getCarId());
		Additional additional= this.additionalRepository.getById(updateRentalRequest.getAdditionalId());
		
		long time = calculateTotalDay(rentalToUpdate);
		double totalPrice= car.getDailyPrice()*time;
		if (rentalToUpdate.getPickCity().getId()!=rentalToUpdate.getReturnCity().getId()) {
			totalPrice= totalPrice+750;			
		}
	//	totalPrice = totalPrice + (additional.getAdditionalPrice());
		rentalToUpdate.setTotalPrice(totalPrice);
		rentalToUpdate.setCar(car);

		this.rentalRepository.save(rentalToUpdate);
		return new SuccessResult("RENTAL.UPDATED");

	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		this.rentalRepository.deleteById(deleteRentalRequest.getId());
		return new SuccessResult("RENTAL.DELETED");
	}

	@Override
	public DataResult<List<GetAllRentalsResponse>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalsResponse> response = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllRentalsResponse>>(response);

	}

	@Override
	public DataResult<ReadRentalResponse> getById(int id) {
		Rental rental = this.rentalRepository.getById(id);
		ReadRentalResponse response = this.modelMapperService.forResponse().map(rental, ReadRentalResponse.class);
		return new SuccessDataResult<ReadRentalResponse>(response);

	}

	private long calculateTotalDay(Rental rental) {
		long dayDifference = (rental.getReturnDate().getTime() - rental.getPickUpDate().getTime());
		long time = TimeUnit.DAYS.convert(dayDifference, TimeUnit.MILLISECONDS);
		rental.setTotalDays((int) time);
		return time;
	}

}
