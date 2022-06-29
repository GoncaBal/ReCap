package com.kodlamaio.rentACar.business.concretes;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.business.requests.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individualCustomers.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.responses.individualCustomers.ReadIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private ModelMapperService modelMapperService;
	private IndividualCustomerRepository individualCustomerRepository;
	private PersonCheckService personCheckService;
	

	@Autowired
	public IndividualCustomerManager(ModelMapperService modelMapperService,
			IndividualCustomerRepository individualCustomerRepository, PersonCheckService personCheckService) {

		this.modelMapperService = modelMapperService;
		this.individualCustomerRepository = individualCustomerRepository;
		this.personCheckService = personCheckService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws NumberFormatException, RemoteException {

		checkIfCustomerExistNationalIdentification(createIndividualCustomerRequest.getNationalIdentification());
		checkIfCustomerExistByeMail(createIndividualCustomerRequest.getEmail());
		checkCustomer(createIndividualCustomerRequest);
	
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("INDIVIDUAL.CUSTOMER.ADDED");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
			throws NumberFormatException, RemoteException {

		checkIfExistIndividualCustomerId(updateIndividualCustomerRequest.getIndividualId());
		checkCustomer(updateIndividualCustomerRequest);
		checkIfNationalIdentificationIsSameForUpdate(updateIndividualCustomerRequest.getIndividualId(),
				updateIndividualCustomerRequest.getNationalIdentification());
		checkIfEmailIsSameForUpdate(updateIndividualCustomerRequest.getIndividualId(),
				updateIndividualCustomerRequest.getEmail());
		

		IndividualCustomer updateToIndividualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerRepository.save(updateToIndividualCustomer);
		return new SuccessResult("INDIVIDUAL.CUSTOMER.UPDATED");

	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		checkIfExistIndividualCustomerId(deleteIndividualCustomerRequest.getIndividualId());
		this.individualCustomerRepository.deleteById(deleteIndividualCustomerRequest.getIndividualId());
		return new SuccessResult("INDIVIDUAL.CUSTOMER.DELETED");
	}

	@Override
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findAll();
		List<GetAllIndividualCustomersResponse> response = individualCustomers.stream()
				.map(individualCustomer -> this.modelMapperService.forResponse().map(individualCustomer,
						GetAllIndividualCustomersResponse.class))
				.collect(Collectors.toList());
		System.out.println(response);
		return new SuccessDataResult<List<GetAllIndividualCustomersResponse>>(response,"INDIVIDUAL.CUSTOMERS.LISTED");
	}
	
	@Override
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findAll(pageable).getContent();
		List<GetAllIndividualCustomersResponse> response = individualCustomers.stream()
				.map(individualCustomer -> this.modelMapperService.forResponse().map(individualCustomer,
						GetAllIndividualCustomersResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllIndividualCustomersResponse>>(response);
	}

	@Override
	public DataResult<ReadIndividualCustomerResponse> getById(int id) {
		checkIfExistIndividualCustomerId(id);
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(id);
		ReadIndividualCustomerResponse response = this.modelMapperService.forResponse().map(individualCustomer,
				ReadIndividualCustomerResponse.class);
		System.out.println(response);
		return new SuccessDataResult<ReadIndividualCustomerResponse>(response);
	}

	private void checkCustomer(CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws NumberFormatException, RemoteException {
		if (!personCheckService.checkIfRealPerson(createIndividualCustomerRequest)) {
			throw new BusinessException("NOT.A.VALID.PERSON");
		}
	}

	private void checkCustomer(UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
			throws NumberFormatException, RemoteException {
		if (!personCheckService.checkIfRealPerson(updateIndividualCustomerRequest)) {
			throw new BusinessException("NOT.A.VALID.PERSON");
		}
	}

	private void checkIfCustomerExistNationalIdentification(String identity) {
		IndividualCustomer currentIndividualCustomer = this.individualCustomerRepository
				.findByNationalIdentification(identity);
		if (currentIndividualCustomer != null) {
			throw new BusinessException("EXIST.CUSTOMER.NATIONAL.IDENTIFICATION");
		}
	}

	private void checkIfExistIndividualCustomerId(int id) {
		IndividualCustomer currentIndividualCustomer = this.individualCustomerRepository.findById(id);
		if (currentIndividualCustomer == null) {
			throw new BusinessException("INVALID.INDIVIDUAL.CUSTOMER.ID");
		}
	}

	private void checkIfNationalIdentificationIsSameForUpdate(int individualId, String nationalIdentification) {
		IndividualCustomer currentIndividualCustomer = this.individualCustomerRepository.findById(individualId);
		if (!currentIndividualCustomer.getNationalIdentification().equals(nationalIdentification)) {
			checkIfCustomerExistNationalIdentification(nationalIdentification);
		}
	}

	private void checkIfCustomerExistByeMail(String email) {
		IndividualCustomer currentIndividualCustomer = this.individualCustomerRepository.findByemail(email);
		if (currentIndividualCustomer != null) {
			throw new BusinessException("INDIVIDUAL.CUSTOMER.MAIL.EXIST");
		}
	}

	private void checkIfEmailIsSameForUpdate(int individualId, String email) {
		IndividualCustomer currentIndividualCustomer = this.individualCustomerRepository.findById(individualId);
		if (!currentIndividualCustomer.getEmail().equals(email)) {
			checkIfCustomerExistByeMail(email);
		}
	}

	@Override
	public IndividualCustomer getIndividualCustomerById(int id) {
		checkIfExistIndividualCustomerId(id);
		return this.individualCustomerRepository.findById(id);
	}


}
