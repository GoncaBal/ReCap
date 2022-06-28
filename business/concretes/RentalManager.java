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
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private FindeksScoreCheckService findeksScoreCheckService;
	private IndividualCustomerRepository individualCustomerRepository;
	private CorporateCustomerRepository corporateCustomerRepository;

	@Autowired
	public RentalManager(RentalRepository rentalRepository, CarRepository carRepository,
			ModelMapperService modelMapperService, FindeksScoreCheckService findeksScoreCheckService,
			IndividualCustomerRepository individualCustomerRepository,
			CorporateCustomerRepository corporateCustomerRepository) {
		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
		this.findeksScoreCheckService = findeksScoreCheckService;
		this.individualCustomerRepository = individualCustomerRepository;
		this.corporateCustomerRepository = corporateCustomerRepository;

	}

	@Override
	public Result addIndividualCustomerForRental(CreateRentalRequest createRentalRequest) {

		checkIfExistCarId(createRentalRequest.getCarId());
		checkFindeksMinValue(createRentalRequest.getCarId(), createRentalRequest.getCustomerId());
		checkIfExistIndividualCustomer(createRentalRequest.getCustomerId());
		checkIfAvailableState(createRentalRequest.getCarId());

		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		Car car = this.carRepository.findById(createRentalRequest.getCarId());

		calculateTotalPrice(rental, car);
		this.rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result addCorporateCustomerForRental(CreateRentalRequest createRentalRequest) {

		checkIfExistCarId(createRentalRequest.getCarId());
		checkIfExistCorporateCustomer(createRentalRequest.getCustomerId());
		checkIfAvailableState(createRentalRequest.getCarId());

		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		Car car = this.carRepository.findById(createRentalRequest.getCarId());

		calculateTotalPrice(rental, car);
		this.rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result updateIndividualCustomerForRental(UpdateRentalRequest updateRentalRequest) {

		checkIfExistRentalId(updateRentalRequest.getId());
		checkIfExistCarId(updateRentalRequest.getCarId());
		checkFindeksMinValue(updateRentalRequest.getCarId(), updateRentalRequest.getCustomerId());
		checkIfExistIndividualCustomer(updateRentalRequest.getCustomerId());
		checkIfAvailableState(updateRentalRequest.getCarId());

		Rental updateToRental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());

		calculateTotalPrice(updateToRental, car);
		this.rentalRepository.save(updateToRental);
		return new SuccessResult("RENTAL.UPDATED");
	}

	@Override
	public Result updateCorporateCustomerForRental(UpdateRentalRequest updateRentalRequest) {

		checkIfExistRentalId(updateRentalRequest.getId());
		checkIfExistCarId(updateRentalRequest.getCarId());
		checkIfExistCorporateCustomer(updateRentalRequest.getCustomerId());
		checkIfAvailableState(updateRentalRequest.getCarId());

		Rental updateToRental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());

		calculateTotalPrice(updateToRental, car);
		this.rentalRepository.save(updateToRental);
		return new SuccessResult("RENTAL.UPDATED");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {

		checkIfExistRentalId(deleteRentalRequest.getId());

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

		checkIfExistRentalId(id);

		Rental rental = this.rentalRepository.findById(id);
		ReadRentalResponse response = this.modelMapperService.forResponse().map(rental, ReadRentalResponse.class);
		return new SuccessDataResult<ReadRentalResponse>(response);
	}

	private long calculateTotalDay(Rental rental) {
		long dayDifference = (rental.getReturnDate().getTime() - rental.getPickUpDate().getTime());
		if (dayDifference < 0) {
			throw new BusinessException("INVALID DATE");
		}
		long time = TimeUnit.DAYS.convert(dayDifference, TimeUnit.MILLISECONDS);
		rental.setTotalDays((int) time);
		return time;
	}

	private void calculateTotalPrice(Rental rental, Car car) {
		double totalPrice = 0;
		long time = calculateTotalDay(rental);
		totalPrice = car.getDailyPrice() * time;
		if ((rental.getPickCity().getId()) != (rental.getReturnCity().getId())) {
			totalPrice = totalPrice + 750;
		}

		rental.setTotalPrice(totalPrice);
	}

	private void checkIfAvailableState(int id) {
		Car currentCar = this.carRepository.findById(id);
		if (currentCar.getState() == 1) {
			currentCar.setState(3);
		} else {
			throw new BusinessException("NOT.AVAILABLE.STATE");
		}
	}

	private boolean checkFindeksMinValue(int carId, int customerId) {
		int score = getMinFindexScoreOfCar(carId);
		String nationalIdentification = getNationalIdentificationOfIndividualCustomer(customerId);

		boolean state = false;
		if (findeksScoreCheckService.checkFindeksScore(nationalIdentification) > score) {
			state = true;
		} else {
			state = false;
			throw new BusinessException("INSUFFICIENT.FINDEKS");
		}
		return state;
	}

	private String getNationalIdentificationOfIndividualCustomer(int customerId) {
		String nationalIdentification = individualCustomerRepository.findById(customerId).getNationalIdentification();
		return nationalIdentification;
	}

	private int getMinFindexScoreOfCar(int carId) {
		checkIfExistCarId(carId);
		return carRepository.findById(carId).getMinFindeksScore();
	}

	private void checkIfExistRentalId(int id) {
		Rental currentRental = this.rentalRepository.findById(id);
		if (currentRental == null) {
			throw new BusinessException("INVALID.ID");
		}
	}

	private void checkIfExistCarId(int id) {
		Car currentCar = this.carRepository.findById(id);
		if (currentCar == null) {
			throw new BusinessException("INVALID.CAR.ID");
		}
	}

	private void checkIfExistIndividualCustomer(int individualId) {
		IndividualCustomer currentCustomer = this.individualCustomerRepository.findById(individualId);
		if (currentCustomer == null) {
			throw new BusinessException("INVALID.INDIVIDUAL.CUSTOMER");
		}
	}

	private void checkIfExistCorporateCustomer(int corporateId) {
		CorporateCustomer currentCustomer = this.corporateCustomerRepository.findById(corporateId);
		if (currentCustomer == null) {
			throw new BusinessException("INVALID.CORPORATE.CUSTOMER");
		}
	}
}
