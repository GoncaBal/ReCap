package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

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

	public MaintenanceManager(MaintenanceRepository maintenanceRepository, ModelMapperService modelMapperService,
			CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.modelMapperService = modelMapperService;
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		checkIfExistCarId(createMaintenanceRequest.getCarId());
		checkIfDates(maintenance);
		checkIfAvailableState(maintenance);
		this.maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		checkIfExistMaintenanceId(updateMaintenanceRequest.getId());
		checkIfExistCarId(updateMaintenanceRequest.getCarId());
		Maintenance updateToMaintenance = this.modelMapperService.forRequest().map(updateMaintenanceRequest,
				Maintenance.class);
		checkIfDates(updateToMaintenance);
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

	private void checkIfDates(Maintenance maintenance) {
		long dayDifference = (maintenance.getDateReturned().getTime() - maintenance.getDateSent().getTime());
		if (dayDifference < 0) {
			throw new BusinessException("INVALID DATE");
		}
	}
	
	private void checkIfAvailableState(Maintenance maintenance) {
		Car currentCar = this.carRepository.findById(maintenance.getCar().getId());
		if (currentCar.getState() == 1 || currentCar.getState()==3) {
			currentCar.setState(2);
		} else {
			throw new BusinessException("NOT.AVAILABLE.STATE");
		}
	}
}
