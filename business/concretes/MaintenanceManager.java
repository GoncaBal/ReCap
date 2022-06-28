package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.GetAllMaintenancesResponse;
import com.kodlamaio.rentACar.business.responses.maintenances.ReadMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {

	private MaintenanceRepository maintenanceRepository;
	private ModelMapperService modelMapperService;
	private CarRepository carRepository;

	@Autowired
	public MaintenanceManager(MaintenanceRepository maintenanceRepository, ModelMapperService modelMapperService,
			CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.modelMapperService = modelMapperService;
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		
		checkIfExistCarId(createMaintenanceRequest.getCarId());
		checkIfDates(createMaintenanceRequest.getMaintenanceId());
		checkIfCarRentedState(createMaintenanceRequest.getCarId());
		checkIfCarMaintenancedState(createMaintenanceRequest.getMaintenanceId());
		
		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		
		this.maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		
		checkIfExistMaintenanceId(updateMaintenanceRequest.getMaintenanceId());
		checkIfExistCarId(updateMaintenanceRequest.getCarId());
		checkIfDates(updateMaintenanceRequest.getMaintenanceId());
		checkCarChangeInUpdate(updateMaintenanceRequest); //tekrar dönülecek
		
		Maintenance updateToMaintenance = this.modelMapperService.forRequest().map(updateMaintenanceRequest,
				Maintenance.class);
		
		this.maintenanceRepository.save(updateToMaintenance);
		return new SuccessResult("MAINTENANCE.UPDATED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		
		checkIfExistMaintenanceId(deleteMaintenanceRequest.getId());
		
		maintenanceRepository.deleteById(deleteMaintenanceRequest.getId());
		return new SuccessResult("MAINTENANCE.DELETED");
	}

	@Override
	public DataResult<List<GetAllMaintenancesResponse>> getAll() {
		List<Maintenance> maintenances = this.maintenanceRepository.findAll();
		List<GetAllMaintenancesResponse> response = maintenances.stream().map(
				maintenance -> this.modelMapperService.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllMaintenancesResponse>>(response);

	}

	@Override
	public DataResult<ReadMaintenanceResponse> getById(int id) {
		
		checkIfExistMaintenanceId(id);
		
		Maintenance maintenance = this.maintenanceRepository.findById(id);
		ReadMaintenanceResponse response = this.modelMapperService.forResponse().map(maintenance,
				ReadMaintenanceResponse.class);
		return new SuccessDataResult<ReadMaintenanceResponse>(response);
	}

	private void checkIfExistMaintenanceId(int id) {
		Maintenance currentMaintenance = this.maintenanceRepository.findById(id);
		if (currentMaintenance == null) {
			throw new BusinessException("INVALID.MAINTENANCE.ID");
		}
	}

	private void checkIfExistCarId(int id) {
		Car currentCar = this.carRepository.findById(id);
		if (currentCar == null) {
			throw new BusinessException("INVALID.CAR.ID");
		}
	}

	private void checkIfDates(int maintenanceId) {
		Maintenance currentMaintenance=this.maintenanceRepository.findById(maintenanceId);
		long dayDifference = (currentMaintenance.getDateReturned().getTime() - currentMaintenance.getDateSent().getTime());
		if (dayDifference < 0) {
			throw new BusinessException("INVALID.DATE");
		}
	}
	
	private void checkCarChangeInUpdate(UpdateMaintenanceRequest updateMaintenanceRequest) {
		Maintenance currentMaintenance = this.maintenanceRepository.findById(updateMaintenanceRequest.getMaintenanceId());
		Car oldCar = currentMaintenance.getCar(); 
		
		if(updateMaintenanceRequest.getCarId() != oldCar.getId()) {
			oldCar.setState(1);
			updateState(updateMaintenanceRequest);
		}
	}
	
	private void checkIfCarRentedState(int carId) {
		Car currentCar=this.carRepository.findById(carId);
		if (currentCar.getState()==3) {
			throw new BusinessException("CAR.IS.RENTED");		
		}
	}
	private void checkIfCarMaintenancedState(int maintenanceId) {
		Car currentCar=this.carRepository.findById(maintenanceId);
		if (currentCar.getState()==2) {
			throw new BusinessException("CAR.IS.MAINTENANCED");
		}
	}


	@Override
	public Result updateState(UpdateMaintenanceRequest updateMaintenanceRequest) {
		checkIfExistCarId(updateMaintenanceRequest.getCarId());
		
		Car currentCar=this.carRepository.findById(updateMaintenanceRequest.getCarId());
		if (currentCar.getState()==1) {
			currentCar.setState(2);
		}else {
			currentCar.setState(1);
		}
		this.carRepository.save(currentCar);
		return new SuccessResult();
	}
}
