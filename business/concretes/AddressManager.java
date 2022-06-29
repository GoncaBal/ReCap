package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.requests.addresses.CreateDifferentAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.CreateSameAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateDifferentAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateSameAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.business.responses.addresses.ReadAddressResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AddressRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.Address;
import com.kodlamaio.rentACar.entities.concretes.Customer;

@Service
public class AddressManager implements AddressService {

	private AddressRepository addressRepository;
	private ModelMapperService modelMapperService;
	private CustomerRepository customerRepository;

	@Autowired
	public AddressManager(AddressRepository addressRepository, ModelMapperService modelMapperService,
			CustomerRepository customerRepository) {

		this.addressRepository = addressRepository;
		this.modelMapperService = modelMapperService;
		this.customerRepository = customerRepository;
	}

	@Override
	public Result addSameAddress(CreateSameAddressRequest createSameAddressRequest) {

		checkIfExistCustomerId(createSameAddressRequest.getCustomerId());

		Address address = this.modelMapperService.forRequest().map(createSameAddressRequest, Address.class);
		address.setInvoiceAddress(address.getContactAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESSES.ADDED");
	}

	@Override
	public Result addDifferentAddress(CreateDifferentAddressRequest createDifferentAddressRequest) {

		checkIfExistCustomerId(createDifferentAddressRequest.getCustomerId());

		Address address = this.modelMapperService.forRequest().map(createDifferentAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESSES.ADDED");
	}

	@Override
	public Result updateSameAddress(UpdateSameAddressRequest updateAddressRequest) {

		checkIfExistAddressId(updateAddressRequest.getId());
		checkIfExistCustomerId(updateAddressRequest.getCustomerId());

		Address updateToAddress = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		updateToAddress.setInvoiceAddress(updateAddressRequest.getContactAdress());
		this.addressRepository.save(updateToAddress);
		return new SuccessResult("ADRESSES.UPDATED");
	}

	@Override
	public Result updateDifferentAddress(UpdateDifferentAddressRequest updateDifferentAddressRequest) {

		checkIfExistAddressId(updateDifferentAddressRequest.getId());
		checkIfExistCustomerId(updateDifferentAddressRequest.getCustomerId());

		Address updateToAddress = this.modelMapperService.forRequest().map(updateDifferentAddressRequest,
				Address.class);
		this.addressRepository.save(updateToAddress);
		return new SuccessResult("ADRESSES.UPDATED");
	}

	@Override
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		checkIfExistAddressId(deleteAddressRequest.getId());

		this.addressRepository.deleteById(deleteAddressRequest.getId());
		return new SuccessResult("ADDRESSES.DELETED");
	}

	@Override
	public DataResult<List<GetAllAddressesResponse>> getAll() {
		List<Address> addresses = this.addressRepository.findAll();
		List<GetAllAddressesResponse> response = addresses.stream()
				.map(address -> this.modelMapperService.forResponse().map(address, GetAllAddressesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressesResponse>>(response);
	}

	@Override
	public DataResult<ReadAddressResponse> getById(int id) {
		checkIfExistAddressId(id);

		Address address = this.addressRepository.findById(id);
		ReadAddressResponse response = this.modelMapperService.forResponse().map(address, ReadAddressResponse.class);
		return new SuccessDataResult<ReadAddressResponse>(response);
	}

	private void checkIfExistAddressId(int id) {
		Address currentAddress = this.addressRepository.findById(id);
		if (currentAddress == null) {
			throw new BusinessException("INVALID.ADDRESS.ID");
		}
	}

	private void checkIfExistCustomerId(int customerId) {

		Customer currentCustomer = this.customerRepository.findById(customerId);
		if (currentCustomer == null) {
			throw new BusinessException("INVALID.CUSTOMER.ID");
		}
	}
}
