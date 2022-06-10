package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.GetAllCarsResponse;
import com.kodlamaio.rentACar.business.responses.cars.ReadCarResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest); 
		
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateCarRequest updateCarRequest) {
		this.carService.update(updateCarRequest);
	}
	
	@PostMapping("/delete")
	public void delete(@RequestBody DeleteCarRequest deleteCarRequest) {
		this.carService.delete(deleteCarRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<GetAllCarsResponse>>  getAll() {
		return this.carService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<ReadCarResponse> getById(@RequestBody  int id) {
		return carService.getById(id);
	}
	// 3 nesne için de crud list all getby id 
}
