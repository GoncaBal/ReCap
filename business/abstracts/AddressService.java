package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.addresses.CreateDifferentAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.CreateSameAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateDifferentAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateSameAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.business.responses.addresses.ReadAddressResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AddressService {

	Result addSameAddress(CreateSameAddressRequest createAddressRequest);

	Result addDifferentAddress(CreateDifferentAddressRequest createDifferentAddressRequest);

	Result updateSameAddress(UpdateSameAddressRequest updateAddressRequest);

	Result updateDifferentAddress(UpdateDifferentAddressRequest updateDifferentAddressRequest);

	Result delete(DeleteAddressRequest deleteAddressRequest);

	DataResult<List<GetAllAddressesResponse>> getAll();

	DataResult<ReadAddressResponse> getById(int id);
}
