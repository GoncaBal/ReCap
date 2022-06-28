package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.requests.corporateCustomers.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporateCustomers.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporateCustomers.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.corporateCustomers.GetAllCorporateCustomersResponse;
import com.kodlamaio.rentACar.business.responses.corporateCustomers.ReadCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{

	ModelMapperService modelMapperService;
	CorporateCustomerRepository corporateCustomerRepository;
	
	@Autowired
	public CorporateCustomerManager(ModelMapperService modelMapperService,
			CorporateCustomerRepository corporateCustomerRepository) {
		this.modelMapperService = modelMapperService;
		this.corporateCustomerRepository = corporateCustomerRepository;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		
		checkIfCustomerExistCompanyName(createCorporateCustomerRequest.getCompanyName());
		checkIfCustomerExistByeMail(createCorporateCustomerRequest.getEmail());
		checkIfExistTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer=this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE.CUSTOMER.ADDED");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		
		checkIfExistCorporateCustomerId(updateCorporateCustomerRequest.getCorporateId());
		checkIfEmailIsSameForUpdate(updateCorporateCustomerRequest.getCorporateId(),updateCorporateCustomerRequest.getEmail());
		checkIfTaxNumberIsSameForUpdate(updateCorporateCustomerRequest.getCorporateId(), updateCorporateCustomerRequest.getTaxNumber());
		checkIfCompanyNameIsSameForUpdate(updateCorporateCustomerRequest.getCorporateId(), updateCorporateCustomerRequest.getCompanyName());
		
		CorporateCustomer updateToCorporateCustomer=this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerRepository.save(updateToCorporateCustomer);
		return new SuccessResult("CORPORATE.CUSTOMER.UPDATED");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		
		checkIfExistCorporateCustomerId(deleteCorporateCustomerRequest.getCorporateId());
		
		this.corporateCustomerRepository.deleteById(deleteCorporateCustomerRequest.getCorporateId());
		return new SuccessResult("CORPORATE.CUSTOMER.DELETED");
	}

	@Override
	public DataResult<List<GetAllCorporateCustomersResponse>> getAll() {
		
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerRepository.findAll();
		List<GetAllCorporateCustomersResponse> response = corporateCustomers.stream()
				.map(corporateCustomer -> this.modelMapperService.forResponse().map(corporateCustomer, GetAllCorporateCustomersResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCorporateCustomersResponse>>(response);
	}

	@Override
	public DataResult<List<GetAllCorporateCustomersResponse>> getAll(int pageNumber, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerRepository.findAll(pageable).getContent();
		List<GetAllCorporateCustomersResponse> response = corporateCustomers.stream()
				.map(corporateCustomer -> this.modelMapperService.forResponse().map(corporateCustomer, GetAllCorporateCustomersResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCorporateCustomersResponse>>(response);
	}

	@Override
	public DataResult<ReadCorporateCustomerResponse> getById(int id) {
		
		checkIfExistCorporateCustomerId(id);
		
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id);
		ReadCorporateCustomerResponse response = this.modelMapperService.forResponse().map(corporateCustomer, ReadCorporateCustomerResponse.class);
		return new SuccessDataResult<ReadCorporateCustomerResponse>(response);
	}

	
	private void checkIfExistCorporateCustomerId(int id) {
		CorporateCustomer currentCorporateCustomer=this.corporateCustomerRepository.findById(id);
		if (currentCorporateCustomer==null) {
			throw new BusinessException("INVALID.CORPORATE.CUSTOMER.ID");
		}
	}
	
	
	private void checkIfCustomerExistCompanyName(String companyName) {
		CorporateCustomer currentCorporateCustomer=this.corporateCustomerRepository.findByCompanyName(companyName);
		if (currentCorporateCustomer!=null) {
			throw new BusinessException("CORPORATE.CUSTOMER.COMPANY.NAME.EXIST");
		}
	}
	
	private void checkIfCompanyNameIsSameForUpdate(int corporateId,String companyName) {
		CorporateCustomer currentCorporateCustomer=this.corporateCustomerRepository.findById(corporateId);
		if (!currentCorporateCustomer.getCompanyName().equals(companyName)) {
			checkIfCustomerExistCompanyName(companyName);
		}
	}
	
	
	private void checkIfCustomerExistByeMail(String email) {
		CorporateCustomer currentCorporateCustomer = this.corporateCustomerRepository.findByemail(email);
		if (currentCorporateCustomer != null) {
			throw new BusinessException("CORPORATE.CUSTOMER.MAIL.EXIST");
		}
	}
	
	
	private void checkIfEmailIsSameForUpdate(int corporateId,String email) {
		CorporateCustomer currentCorporateCustomer=this.corporateCustomerRepository.findById(corporateId);
		if (!currentCorporateCustomer.getEmail().equals(email)) {
			checkIfCustomerExistByeMail(email);
		}
	}
	
	
	private void checkIfExistTaxNumber(String taxNumber) {
		CorporateCustomer currentCorporateCustomer=this.corporateCustomerRepository.findByTaxNumber(taxNumber);
		if (currentCorporateCustomer!=null) {
			throw new BusinessException("CORPORATE.CUSTOMER.TAX.NUMBER.EXIST");
		}
	}
	
	
	private void checkIfTaxNumberIsSameForUpdate(int corporateId,String taxNumber) {
		CorporateCustomer currentCorporateCustomer =this.corporateCustomerRepository.findById(corporateId);
		if (!currentCorporateCustomer.getTaxNumber().equals(taxNumber)) {
			checkIfExistTaxNumber(taxNumber);
		}
	}

	@Override
	public CorporateCustomer getCorporateCustomerById(int id) {
		checkIfExistCorporateCustomerId(id);
		return this.corporateCustomerRepository.findById(id);
	}
	
}
