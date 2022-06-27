package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.GetAllBrandsResponse;
import com.kodlamaio.rentACar.business.responses.brands.ReadBrandResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;

	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@PostMapping("/add")

	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllBrandsResponse>> getAll() {
		return brandService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<ReadBrandResponse> getById(@RequestParam @Valid int id) {
		return brandService.getById(id);
	}
}
