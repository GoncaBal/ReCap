package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.additionalItems.ReadAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

public interface AdditionalItemService {
	
	
	Result add(CreateAdditionalItemRequest createAdditionalItemRequest);

	Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest);

	Result delete(DeleteAdditionalItemRequest deleteAdditionalItemRequest);

	DataResult<List<GetAllAdditionalItemsResponse>> getAll();

	DataResult<ReadAdditionalItemsResponse> getById(int id);
	
	AdditionalItem getAdditionalItemById(int additionalItemId);
}
