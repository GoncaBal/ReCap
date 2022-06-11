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

	MaintenanceRepository maintenanceRepository;
	ModelMapperService modelMapperService;
	CarRepository carRepository;
	
	
	@Autowired  
	public MaintenanceManager(MaintenanceRepository maintenanceRepository,ModelMapperService modelMapperService,CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.modelMapperService=modelMapperService;
		this.carRepository=carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
	
		Maintenance maintenance=this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		Car car = this.carRepository.getById(createMaintenanceRequest.getCarId());
		car.setId(createMaintenanceRequest.getCarId());
		car.setState(2);
		maintenance.setCar(car);
		
		this.maintenanceRepository.save(maintenance);

		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
//		Maintenance maintenanceToUpdate = maintenanceRepository.findById(updateMaintenanceRequest.getId());
//
//		maintenanceToUpdate.setDateReturned(updateMaintenanceRequest.getDateReturned());
//		maintenanceToUpdate.setDateSent(updateMaintenanceRequest.getDateSent());
//
//		Car  car=this.carRepository.findById(updateMaintenanceRequest.getCarId());
//		car.setId(updateMaintenanceRequest.getCarId());
//		State state = new State();
//		state.setId(1);
//		maintenanceToUpdate.setCar(car);
//		LocalDate date=LocalDate.now();
//		if (date.equals(maintenanceToUpdate.getDateReturned())) {
//			maintenanceToUpdate.setState(state);;
//		}else {
//			state.setId(2);
//		}
		
		Maintenance maintenanceToUpdate=this.modelMapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
		this.maintenanceRepository.save(maintenanceToUpdate);
		return new SuccessResult("MAINTENANCE.UPDATED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		maintenanceRepository.deleteById(deleteMaintenanceRequest.getId());
		return new SuccessResult("MAINTENANCE.DELETED");
	}

	@Override
	public DataResult<List<GetAllMaintenancesResponse>> getAll() {
		//return new SuccessDataResult<List<Maintenance>>(maintenanceRepository.findAll(), "MAINTENANCE.LISTED");
		List<Maintenance> maintenances=this.maintenanceRepository.findAll();
		List<GetAllMaintenancesResponse> response= maintenances.stream().map(maintenance->this.modelMapperService.forResponse().map(maintenance, GetAllMaintenancesResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllMaintenancesResponse>>(response);
		
	}

	@Override
	public DataResult<ReadMaintenanceResponse> getById( int id) {
		Maintenance maintenance= this.maintenanceRepository.getById(id);
		ReadMaintenanceResponse response =
				this.modelMapperService.forResponse().map(maintenance, ReadMaintenanceResponse.class);
		return new SuccessDataResult<ReadMaintenanceResponse>(response);
	}

}
