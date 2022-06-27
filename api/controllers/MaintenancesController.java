package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.GetAllMaintenancesResponse;
import com.kodlamaio.rentACar.business.responses.maintenances.ReadMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenancesController {

	private MaintenanceService maintenanceService;

	public MaintenancesController(MaintenanceService maintenanceService) {
		this.maintenanceService = maintenanceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateMaintenanceRequest createMaintenanceRequest) {
		return this.maintenanceService.add(createMaintenanceRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateMaintenanceRequest updateMaintenanceRequest) {
		return this.maintenanceService.update(updateMaintenanceRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteMaintenanceRequest deleteMaintenanceRequest) {
		return this.maintenanceService.delete(deleteMaintenanceRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllMaintenancesResponse>> getAll() {
		return this.maintenanceService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadMaintenanceResponse> getbyid(@RequestParam @Valid int id) {
		return this.maintenanceService.getById(id);
	}

}
