package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.DeleteAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.UpdateAdditionalRequest;
import com.kodlamaio.rentACar.business.responses.additionals.GetAllAdditionalsResponse;
import com.kodlamaio.rentACar.business.responses.additionals.ReadAdditionalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AdditionalService {
	Result add(CreateAdditionalRequest createAdditionalRequest);

	Result update(UpdateAdditionalRequest updateAdditionalRequest);

	Result delete(DeleteAdditionalRequest deleteAdditionalRequest);

	DataResult<List<GetAllAdditionalsResponse>> getAll();

	DataResult<ReadAdditionalResponse> getById(int id);
}
