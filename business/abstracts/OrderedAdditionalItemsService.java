package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.CreateOrderedAdditionalItemsRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.DeleteOrderedAdditionalsItemsRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItems.UpdateOrderedAdditionalItemsRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.ReadOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalItemsService {

	Result add(CreateOrderedAdditionalItemsRequest createAdditionalRequest);

	Result update(UpdateOrderedAdditionalItemsRequest updateAdditionalRequest);

	Result delete(DeleteOrderedAdditionalsItemsRequest deleteAdditionalRequest);

	DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll();

	DataResult<ReadOrderedAdditionalItemsResponse> getById(int id);
}
