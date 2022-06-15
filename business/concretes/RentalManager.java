package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.FindeksScoreCheckService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Customer;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {
	@Autowired
	RentalRepository rentalRepository;
	@Autowired
	CarRepository carRepository;
	@Autowired
	ModelMapperService modelMapperService;
	@Autowired
	FindeksScoreCheckService findeksScoreCheckService;
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {

		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		Car car = this.carRepository.getById(createRentalRequest.getCarId());
		Customer customer=this.customerRepository.getById(createRentalRequest.getCustomerId());
		if (checkFindeksMinValue(car.getMinFindeksScore(), customer.getNationalIdentification())) {
		car.setState(3);
		long time = calculateTotalDay(rental);
		double totalPrice = car.getDailyPrice() * time;
		if ((rental.getPickCity().getId()) != (rental.getReturnCity().getId())) {
			totalPrice = totalPrice + 750;
		}

		rental.setTotalPrice(totalPrice);
		rental.setCar(car);

		this.rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
		}else {
			return new ErrorResult("INSUFFICIENT.FINDEKS.POINTS.");
		}
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rentalToUpdate = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car car = this.carRepository.getById(updateRentalRequest.getCarId());

		long time = calculateTotalDay(rentalToUpdate);
		double totalPrice = car.getDailyPrice() * time;
		if (rentalToUpdate.getPickCity().getId() != rentalToUpdate.getReturnCity().getId()) {
			totalPrice = totalPrice + 750;
		}
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

	private boolean checkFindeksMinValue(int score, String nationalityIdentification) {
		boolean state = false;
		if (findeksScoreCheckService.checkFindeksScore(nationalityIdentification) > score) {
			state = true;
		} else {
			state = false;
		}
		return state;
	}

}
