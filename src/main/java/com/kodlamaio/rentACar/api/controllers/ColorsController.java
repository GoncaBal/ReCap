package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.DeleteColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.GetAllColorsResponse;
import com.kodlamaio.rentACar.business.responses.colors.ReadColorResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	@Autowired
	private ColorService colorService;

	@PostMapping("/add")
	public void add(@RequestBody @Valid CreateColorRequest createColorRequest) {
		this.colorService.add(createColorRequest);
	}

	@PostMapping("/delete")
	public void delete(@RequestBody @Valid DeleteColorRequest deleteColorRequest) {
		this.colorService.delete(deleteColorRequest);
	}

	@PostMapping("/update")
	public void update(@RequestBody @Valid UpdateColorRequest updateColorRequest) {
		this.colorService.update(updateColorRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllColorsResponse>>  getAll() {
		return this.colorService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<ReadColorResponse> getById(@RequestParam @Valid  int id) {
		return this.colorService.getById(id);
	}
}
