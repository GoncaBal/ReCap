package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CustomerService;
import com.kodlamaio.rentACar.business.requests.customers.CreateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.customers.DeleteCustomerRequest;
import com.kodlamaio.rentACar.business.requests.customers.UpdateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.customers.GetAllCustomersResponse;
import com.kodlamaio.rentACar.business.responses.customers.ReadCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	
	

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) {
		checkIfCustomerExistByName(createCustomerRequest.getEMail());
		Customer customer= this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
		this.customerRepository.save(customer);
		return new SuccessResult("CUSTOMER.ADDED");
	}

	@Override
	public Result update(UpdateCustomerRequest updateCustomerRequest) {
		Customer customerToUpdate= this.modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);
		this.customerRepository.save(customerToUpdate);
		return new SuccessResult("CUSTOMER.UPDATED");
	}

	@Override
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) {
		this.customerRepository.deleteById(deleteCustomerRequest.getId());
		return new SuccessResult("CUSTOMER.DELETED");
	}

	@Override
	public DataResult<List<GetAllCustomersResponse>> getAll() {
		List<Customer> customers=this.customerRepository.findAll();
		List<GetAllCustomersResponse> response= customers.stream().map(customer->this.modelMapperService.forResponse().map(customer, GetAllCustomersResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCustomersResponse>>(response);
	}

	@Override
	public DataResult<ReadCustomerResponse> getById(int id) {
		Customer customer = this.customerRepository.getById(id);
		ReadCustomerResponse response = this.modelMapperService.forResponse().map(customer, ReadCustomerResponse.class);
		return new SuccessDataResult<ReadCustomerResponse>(response);
	}

	@Override
	public DataResult<List<GetAllCustomersResponse>> getAll(Integer pageNumber, Integer pageSize) {
		Pageable pageable= PageRequest.of(pageNumber-1, pageSize);
		List<Customer> users = this.customerRepository.findAll(pageable).getContent();
		List<GetAllCustomersResponse> response = users.stream().map(customer -> this.modelMapperService.forResponse()
				.map(customer, GetAllCustomersResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCustomersResponse>>(response);
	}
	private void checkIfCustomerExistByName(String name) {
		Customer currentCustomer=this.customerRepository.findByName(name);
		if (currentCustomer!=null) {
			throw new BusinessException("CUSTOMER.EXIST");
		}

}
}