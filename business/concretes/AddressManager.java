package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

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
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.Address;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

@Service
public class AddressManager implements AddressService {

	private AddressRepository addressRepository;
	private ModelMapperService modelMapperService;
	private IndividualCustomerRepository individualCustomerRepository;
	private CorporateCustomerRepository corporateCustomerRepository;

	public AddressManager(AddressRepository addressRepository, ModelMapperService modelMapperService,IndividualCustomerRepository individualCustomerRepository,CorporateCustomerRepository corporateCustomerRepository) {
		this.addressRepository = addressRepository;
		this.modelMapperService = modelMapperService;
		this.individualCustomerRepository=individualCustomerRepository;
		this.corporateCustomerRepository=corporateCustomerRepository;
	}

	@Override
	public Result addSameAddressForIndividualCustomer(CreateSameAddressRequest createSameAddressRequest) {
		
		checkIfExistIndividualCustomerId(createSameAddressRequest.getCustomerId());
		
		Address address = this.modelMapperService.forRequest().map(createSameAddressRequest, Address.class);
		address.setInvoiceAddress(address.getContactAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESSES.ADDED");
	}
	
	@Override
	public Result addSameAddressForCorporateCustomer(CreateSameAddressRequest createSameAddressRequest) {
		
		checkIfExistCorporateCustomerId(createSameAddressRequest.getCustomerId());
		
		Address address = this.modelMapperService.forRequest().map(createSameAddressRequest, Address.class);
		address.setInvoiceAddress(address.getContactAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESSES.ADDED");
	}
	
	@Override
	public Result addDifferentAddressForIndividualCustomer(CreateDifferentAddressRequest createDifferentAddressRequest) {
		
		checkIfExistIndividualCustomerId(createDifferentAddressRequest.getCustomerId());
		
		Address address = this.modelMapperService.forRequest().map(createDifferentAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESSES.ADDED");
	}
	@Override
	public Result addDifferentAddressForCorporateCustomer(CreateDifferentAddressRequest createDifferentAddressRequest) {
		
		checkIfExistCorporateCustomerId(createDifferentAddressRequest.getCustomerId());
		
		Address address = this.modelMapperService.forRequest().map(createDifferentAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESSES.ADDED");
	}

	@Override
	public Result updateSameAddressForIndividualCustomer(UpdateSameAddressRequest updateAddressRequest) {
		
		checkIfExistAddressId(updateAddressRequest.getId());
		checkIfExistIndividualCustomerId(updateAddressRequest.getCustomerId());
		
		Address updateToAddress = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		updateToAddress.setInvoiceAddress(updateAddressRequest.getContactAdress());
		this.addressRepository.save(updateToAddress);
		return new SuccessResult("ADRESSES.UPDATED");
	}
	@Override
	public Result updateSameAddressForCorporateCustomer(UpdateSameAddressRequest updateAddressRequest) {
		
		checkIfExistAddressId(updateAddressRequest.getId());
		checkIfExistCorporateCustomerId(updateAddressRequest.getCustomerId());
		
		Address updateToAddress = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		updateToAddress.setInvoiceAddress(updateAddressRequest.getContactAdress());
		this.addressRepository.save(updateToAddress);
		return new SuccessResult("ADRESSES.UPDATED");
	}
	
	@Override
	public Result updateDifferentAddressForIndividualCustomer(UpdateDifferentAddressRequest updateDifferentAddressRequest) {
		
		checkIfExistAddressId(updateDifferentAddressRequest.getId());
		checkIfExistIndividualCustomerId(updateDifferentAddressRequest.getCustomerId());
		
		Address updateToAddress = this.modelMapperService.forRequest().map(updateDifferentAddressRequest, Address.class);
		this.addressRepository.save(updateToAddress);
		return new SuccessResult("ADRESSES.UPDATED");
	}
	@Override
	public Result updateDifferentAddressForCorporateCustomer(UpdateDifferentAddressRequest updateDifferentAddressRequest) {
		
		checkIfExistAddressId(updateDifferentAddressRequest.getId());
		checkIfExistCorporateCustomerId(updateDifferentAddressRequest.getCustomerId());
		
		Address updateToAddress = this.modelMapperService.forRequest().map(updateDifferentAddressRequest, Address.class);
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
		Address currentAddress=this.addressRepository.findById(id);
		if (currentAddress==null) {
			throw new BusinessException("INVALID.ADDRESS.ID");
		}
	}
	
	private void checkIfExistCorporateCustomerId(int corporateId) {
		CorporateCustomer currentCorporateCustomer=this.corporateCustomerRepository.findById(corporateId);
		if (currentCorporateCustomer==null) {
			throw new BusinessException("INVALID.CORPORATE.CUSTOMER.ID");
		}
	}
	
	private void checkIfExistIndividualCustomerId(int individualId) {
		
		IndividualCustomer individualCustomer=this.individualCustomerRepository.findById(individualId);
		if (individualCustomer==null) {
			throw new BusinessException("INVALID.INDIVIDUAL.CUSTOMER.ID");
		}
	}
}
