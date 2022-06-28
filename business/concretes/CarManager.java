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
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.ColorRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Color;

@Service
public class CarManager implements CarService {

	private CarRepository carRepository;
	private BrandRepository brandRepository;
	private ColorRepository colorRepository;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarRepository carRepository, BrandRepository brandRepository, ColorRepository colorRepository,
			ModelMapperService modelMapperService) {
		this.carRepository = carRepository;
		this.brandRepository = brandRepository;
		this.colorRepository = colorRepository;
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
	public DataResult<ReadCarResponse> getById(int id) {

		checkIfExistCarId(id);

		Car car = this.carRepository.findById(id);
		ReadCarResponse response = this.modelMapperService.forResponse().map(car, ReadCarResponse.class);
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
	
	//böyle bir carId mevcut mu diye kontrol
	private void checkIfExistCarId(int id) {
		Car currentCar = this.carRepository.findById(id);
		if (currentCar == null) {
			throw new BusinessException("INVALID.CAR.ID");
		}
	}

	//böyle bir brandId mevcut mu diye kontrol
	private void checkIfExistBrandId(int id) {
		Brand currentBrand = this.brandRepository.findById(id);
		if (currentBrand == null) {
			throw new BusinessException("INVALID.BRAND.ID");
		}
	}

	//böyle bir colorId mevcut mu diye kontrol
	private void checkIfExistColorId(int id) {
		Color currentColor = this.colorRepository.findById(id);
		if (currentColor == null) {
			throw new BusinessException("INVALID.COLOR.ID");
		}
	}

	//böyle bir carPlate mevcut mu diye kontrol
	private void checkIfExistCarPlate(String carPlate) {
		Car currentCar = this.carRepository.findByCarPlate(carPlate);
		if (currentCar != null) {
			throw new BusinessException("EXIST.CAR.PLATE");
		}
	}

	//güncelleme yapılan carPLate aynı mı kontrolü
	private void checkIfCarPlateIsSameForUpdate(int carId,String carPlate) {
		Car currentCar=this.carRepository.findById(carId);
		if (!currentCar.getCarPlate().equals(carPlate)) {
			checkIfExistCarPlate(carPlate);
		}
	}
	
	//aynı brand'ten en fazla 5 tane eklenebilir
	private void checkIfBrandCount(int id) {
		List<Car> cars = carRepository.findByBrandId(id);
		if (cars.size() > 4) {
			throw new BusinessException("ERROR:CAR.ADDED");
		}
	}
	
	//güncelleme yapılan brandId aynı mı kontrolü
	private void checkIfBrandIdIsSameForUpdate(int carId,int brandId) {
		Car currentCar=this.carRepository.findById(carId);
		Brand currentBrand=this.brandRepository.findById(brandId);
		if (currentCar.getBrand().getId()!=currentBrand.getId()) {
			checkIfBrandCount(brandId);
		}
	}
}
