package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.ReadCarResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Color;

// ctrl + shift + o kullanılmayan satırları siler.
@Service
public class CarManager implements CarService {

	private CarRepository carRepository;

	@Autowired
	public CarManager(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		if (!checkBrandCount(createCarRequest.getBrandId())) {
			Car car = new Car();
			car.setDescription(createCarRequest.getDescription());
			car.setDailyPrice(createCarRequest.getDailyPrice());
			car.setCarPlate(createCarRequest.getCarPlate());
			car.setKilometer(createCarRequest.getKilometer());
			
			Brand brand = new Brand();
			brand.setId(createCarRequest.getBrandId());
			car.setBrand(brand);

			Color color = new Color();
			color.setId(createCarRequest.getColorId());
			car.setColor(color);
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
		Car carToUpdate = carRepository.findById(updateCarRequest.getId());
		Color color = new Color();
		color.setId(updateCarRequest.getColorId());
		Brand brand = new Brand();
		brand.setId(updateCarRequest.getBrandId());
		carToUpdate.setDescription(updateCarRequest.getDescription());
		carToUpdate.setDailyPrice(updateCarRequest.getDailyPrice());
		carToUpdate.setCarPlate(updateCarRequest.getCarPlate());
		carToUpdate.setKilometer(updateCarRequest.getKilometer());
		carToUpdate.setBrand(brand);
		carToUpdate.setColor(color);

		this.carRepository.save(carToUpdate);
		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult("CAR.DELETED");
	}

	@Override
	public DataResult<List<Car>> getAll() {

		return new SuccessDataResult<List<Car>>(carRepository.findAll(), "CAR.LISTED");
	}

	@Override
	public DataResult<Car> getById(ReadCarResponse readCarResponse) {
		return new SuccessDataResult<Car>(carRepository.findById(readCarResponse.getId()));

	}

}
