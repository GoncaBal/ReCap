package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.ReadMaintenanceResponse;
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

	MaintenanceRepository maintenanceRepository;
	CarRepository carRepository;
	
	@Autowired
	public MaintenanceManager(MaintenanceRepository maintenanceRepository,CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.carRepository=carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		Maintenance maintenance = new Maintenance();
		maintenance.setDateSent(createMaintenanceRequest.getDateSent());
		maintenance.setDateReturned(createMaintenanceRequest.getDateReturned());
		

		Car car = new Car();
		car.setId(createMaintenanceRequest.getCarId());
		maintenance.setCar(car);

		this.maintenanceRepository.save(maintenance);

		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		Maintenance maintenanceToUpdate = maintenanceRepository.findById(updateMaintenanceRequest.getId());

		maintenanceToUpdate.setDateReturned(updateMaintenanceRequest.getDateReturned());
		maintenanceToUpdate.setDateSent(updateMaintenanceRequest.getDateSent());

		Car  car=this.carRepository.findById(updateMaintenanceRequest.getCarId());
		car.setId(updateMaintenanceRequest.getCarId());

		maintenanceToUpdate.setCar(car);
		this.maintenanceRepository.save(maintenanceToUpdate);
		return new SuccessResult("MAINTENANCE.UPDATED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		maintenanceRepository.deleteById(deleteMaintenanceRequest.getId());
		return new SuccessResult("MAINTENANCE.DELETED");
	}

	@Override
	public DataResult<List<Maintenance>> getAll() {
		return new SuccessDataResult<List<Maintenance>>(maintenanceRepository.findAll(), "MAINTENANCE.LISTED");
	}

	@Override
	public DataResult<Maintenance> getById(ReadMaintenanceResponse readMaintenanceResponse) {
		return new SuccessDataResult<Maintenance>(maintenanceRepository.findById(readMaintenanceResponse.getId()));

	}

}
