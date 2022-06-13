package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.additionalItems.ReadAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalitems")
public class AdditionalItemsController {
	
	AdditionalItemService additionalItemService;
	
public AdditionalItemsController(AdditionalItemService additionalItemService) {
	
		this.additionalItemService = additionalItemService;
	}
@PostMapping("/add")
public Result add(@RequestBody CreateAdditionalItemRequest createAdditionalItemRequest) {
	 return this.additionalItemService.add(createAdditionalItemRequest);
}
@PostMapping("/update")
public Result update(@RequestBody UpdateAdditionalItemRequest updateAdditionalItemRequest) {
	return this.additionalItemService.update(updateAdditionalItemRequest);
}
@PostMapping("/delete")
public Result delete(@RequestBody DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
	return this.additionalItemService.delete(deleteAdditionalItemRequest);
}
@GetMapping("/getall")
public DataResult<List<GetAllAdditionalItemsResponse>> getAll(){
	return this.additionalItemService.getAll();
}
@GetMapping("/getbyid")
public DataResult<ReadAdditionalItemsResponse> getById(@RequestParam int id){
	return this.additionalItemService.getById(id);
}
}
