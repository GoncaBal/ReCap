package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.customers.CreateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.customers.DeleteCustomerRequest;
import com.kodlamaio.rentACar.business.requests.customers.UpdateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.customers.GetAllCustomersResponse;
import com.kodlamaio.rentACar.business.responses.customers.ReadCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface CustomerService {
	Result add(CreateCustomerRequest createCustomerRequest);

	Result update(UpdateCustomerRequest creCustomerRequest);

	Result delete(DeleteCustomerRequest deleteCustomerRequest);

	DataResult<List<GetAllCustomersResponse>> getAll();

	DataResult<List<GetAllCustomersResponse>> getAll(Integer pageNumber, Integer pageSize);

	DataResult<ReadCustomerResponse> getById(int id);
}
