package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.GetAllCarsResponse;
import com.kodlamaio.rentACar.business.responses.cars.ReadCarResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;

// ctrl + shift + o kullanılmayan satırları siler.
@Service
public class CarManager implements CarService {

	private CarRepository carRepository;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarRepository carRepository, ModelMapperService modelMapperService) {
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;

	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		if (!checkBrandCount(createCarRequest.getBrandId())) {

			Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
			car.setState(1);
			
			this.carRepository.save(car);
			return new SuccessResult("CAR.ADDED");
		} else {
			return new ErrorResult("ERROR:CAR.ADDED");
		}

	}

	private boolean checkBrandCount(int id) {

		List<Car> cars = carRepository.getByBrandId(id);
		if (cars.size() < 5) {
			return false;
		}
		return true;
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		Car carToUpdate = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(carToUpdate);
		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
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
	public DataResult<ReadCarResponse> getById( int id) {
		Car car = this.carRepository.getById(id);
		ReadCarResponse response= this.modelMapperService.forResponse().map(car, ReadCarResponse.class);
		return new SuccessDataResult<ReadCarResponse>(response);
	}

}
