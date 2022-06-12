package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

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
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalRepository;
import com.kodlamaio.rentACar.entities.concretes.Additional;
@Service
public class AdditionalManager implements AdditionalService{

	ModelMapperService modelMapperService;
	AdditionalRepository additionalRepository;
	
	
	public AdditionalManager(ModelMapperService modelMapperService, AdditionalRepository additionalRepository) {
		this.modelMapperService = modelMapperService;
		this.additionalRepository = additionalRepository;
	}

	@Override
	public Result add(CreateAdditionalRequest createAdditionalItemRequest) {
		Additional additional=this.modelMapperService.forRequest().map(createAdditionalItemRequest, null)
		return null;
	}

	@Override
	public Result update(UpdateAdditionalRequest updateAdditionalItemRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<GetAllAdditionalsResponse>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<ReadAdditionalResponse> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
