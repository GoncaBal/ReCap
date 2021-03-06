package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.GetAllCarsResponse;
import com.kodlamaio.rentACar.business.responses.cars.ReadCarResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarRepository carRepository;
	private BrandService brandService;
	private ColorService colorService;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarRepository carRepository, BrandService brandService, ColorService colorService,
			ModelMapperService modelMapperService) {
		this.carRepository = carRepository;
		this.brandService = brandService;
		this.colorService = colorService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		checkIfExistBrandId(createCarRequest.getBrandId());
		checkIfExistColorId(createCarRequest.getColorId());
		checkIfBrandCount(createCarRequest.getBrandId());
		checkIfExistCarPlate(createCarRequest.getCarPlate());

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setState(1);
		this.carRepository.save(car);
		return new SuccessResult("CAR.ADDED");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		checkIfExistCarId(updateCarRequest.getId());
		checkIfExistBrandId(updateCarRequest.getBrandId());
		checkIfExistColorId(updateCarRequest.getColorId());
		checkIfCarPlateIsSameForUpdate(updateCarRequest.getId(), updateCarRequest.getCarPlate());
		checkIfBrandIdIsSameForUpdate(updateCarRequest.getId(), updateCarRequest.getBrandId());

		Car carToUpdate = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		carToUpdate.setState(1);
		this.carRepository.save(carToUpdate);
		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {

		checkIfExistCarId(deleteCarRequest.getId());

		carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult("CAR.DELETED");
	}

	@Override
	public DataResult<List<GetAllCarsResponse>> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCarsResponse>>(response);
	}

	@Override
	public DataResult<ReadCarResponse> getById(int carId) {

		checkIfExistCarId(carId);
		getRepository(carId);
		ReadCarResponse response = this.modelMapperService.forResponse().map(getRepository(carId),
				ReadCarResponse.class);
		return new SuccessDataResult<ReadCarResponse>(response);
	}

	@Override
	public DataResult<List<GetAllCarsResponse>> getByState(int state) {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.filter(car -> car.getState() == state).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCarsResponse>>(response, "CAR.LISTED");
	}

	
	private void checkIfExistCarId(int carId) {
		if (getRepository(carId) == null) {
			throw new BusinessException("INVALID.CAR.ID");
		}
	}

	
	private void checkIfExistBrandId(int brandId) {

		if (!brandService.getById(brandId).isSuccess()) {
			throw new BusinessException("INVALID.BRAND.ID");
		}
	}

	
	private void checkIfExistColorId(int colorId) {

		if (!colorService.getById(colorId).isSuccess()) {
			throw new BusinessException("INVALID.COLOR.ID");
		}
	}

	
	private void checkIfExistCarPlate(String carPlate) {
		Car currentCar = this.carRepository.findByCarPlate(carPlate);
		if (currentCar != null) {
			throw new BusinessException("EXIST.CAR.PLATE");
		}
	}

	
	private void checkIfCarPlateIsSameForUpdate(int carId, String carPlate) {
		// getRepository(carId);
		Car currentCar = this.carRepository.findById(carId);
		if (currentCar.getCarPlate() != carPlate) {
			checkIfExistCarPlate(carPlate);
		}
	}

	
	private void checkIfBrandCount(int id) {
		List<Car> cars = carRepository.findByBrandId(id);
		if (cars.size() > 4) {
			throw new BusinessException("ERROR:CAR.ADDED");
		}
	}

	
	private void checkIfBrandIdIsSameForUpdate(int carId, int brandId) {

		if (getRepository(carId).getBrand().getId() != brandService.getBrandById(brandId).getId()) {
			checkIfBrandCount(brandId);
		}
	}

	
	@Override
	public Car getCarById(int carId) {
		checkIfExistCarId(carId);
		return this.carRepository.findById(carId);
	}

	private Car getRepository(int carId) {
		Car car = this.carRepository.findById(carId);
		return car;
	}
}
