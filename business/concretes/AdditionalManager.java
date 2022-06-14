package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalService;
import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.DeleteAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.UpdateAdditionalRequest;
import com.kodlamaio.rentACar.business.responses.additionals.GetAllAdditionalsResponse;
import com.kodlamaio.rentACar.business.responses.additionals.ReadAdditionalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalRepository;
import com.kodlamaio.rentACar.entities.concretes.Additional;
@Service
public class AdditionalManager implements AdditionalService{
	@Autowired
	ModelMapperService modelMapperService;
	@Autowired
	private AdditionalRepository additionalRepository;
	
	@Override
	public Result add(CreateAdditionalRequest createAdditionalItemRequest) {
		Additional additional=this.modelMapperService.forRequest().map(createAdditionalItemRequest, Additional.class);
		this.additionalRepository.save(additional);
		return new SuccessResult("ADDITIONAL.ADDED");
	}

	@Override
	public Result update(UpdateAdditionalRequest updateAdditionalItemRequest) {
		Additional additionalToUpdate = this.modelMapperService.forRequest().map(updateAdditionalItemRequest, Additional.class);
		this.additionalRepository.save(additionalToUpdate);
		return new SuccessResult("ADDITIONAL.UPDATED");
	}

	@Override
	public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) {	
		this.additionalRepository.deleteById(deleteAdditionalRequest.getId());
		return new SuccessResult("ADDITIONAL.DELETED");
	}

	@Override
	public DataResult<List<GetAllAdditionalsResponse>> getAll() {
		List<Additional> additionals = this.additionalRepository.findAll();
		List<GetAllAdditionalsResponse> response = additionals.stream()
				.map(additional -> this.modelMapperService.forResponse().map(additional, GetAllAdditionalsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAdditionalsResponse>>(response);
	}

	@Override
	public DataResult<ReadAdditionalResponse> getById(int id) {
		Additional additional = this.additionalRepository.getById(id);
		ReadAdditionalResponse response = this.modelMapperService.forResponse().map(additional, ReadAdditionalResponse.class);
		return new SuccessDataResult<ReadAdditionalResponse>(response);
	}
	


}
