package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.additionalItems.ReadAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

@Service
public class AdditionalItemManager implements AdditionalItemService {

	private ModelMapperService modelMapperService;
	private AdditionalItemRepository additionalItemRepository;

	@Autowired
	public AdditionalItemManager(ModelMapperService modelMapperService,
			AdditionalItemRepository additionalItemRepository) {

		this.modelMapperService = modelMapperService;
		this.additionalItemRepository = additionalItemRepository;
	}

	@Override
	public Result add(CreateAdditionalItemRequest createAdditionalItemRequest) {
		checkIfAdditionalItemExistByName(createAdditionalItemRequest.getName());
		
		AdditionalItem additionalItem = this.modelMapperService.forRequest().map(createAdditionalItemRequest,
				AdditionalItem.class);
		
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("ADDITIONAL.ITEM.ADDED");
	}

	@Override
	public Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		
		checkIfExistAdditionalItemId(updateAdditionalItemRequest.getId());
		checkIfAdditionalItemExistByName(updateAdditionalItemRequest.getName());
		
		AdditionalItem additionalItemToUpdate = this.modelMapperService.forRequest().map(updateAdditionalItemRequest,
				AdditionalItem.class);
		
		this.additionalItemRepository.save(additionalItemToUpdate);
		return new SuccessResult("ADDITIONAL.ITEM.UPDATED");
	}

	@Override
	public Result delete(DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		checkIfExistAdditionalItemId(deleteAdditionalItemRequest.getId());
		
		this.additionalItemRepository.deleteById(deleteAdditionalItemRequest.getId());
		return new SuccessResult("ADDITIONAL.ITEM.DELETED");
	}

	@Override
	public DataResult<List<GetAllAdditionalItemsResponse>> getAll() {
		List<AdditionalItem> additionalItems = this.additionalItemRepository.findAll();
		List<GetAllAdditionalItemsResponse> response = additionalItems.stream()
				.map(additionalItem -> this.modelMapperService.forResponse().map(additionalItem,
						GetAllAdditionalItemsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAdditionalItemsResponse>>(response);
	}

	@Override
	public DataResult<ReadAdditionalItemsResponse> getById(int id) {
		checkIfExistAdditionalItemId(id);
		
		AdditionalItem additionalItem = this.additionalItemRepository.findById(id);
		ReadAdditionalItemsResponse response = this.modelMapperService.forResponse().map(additionalItem,
				ReadAdditionalItemsResponse.class);
		return new SuccessDataResult<ReadAdditionalItemsResponse>(response);
	}

	private void checkIfAdditionalItemExistByName(String name) {
		AdditionalItem currentAdditionalItem = this.additionalItemRepository.findByName(name);
		if (currentAdditionalItem != null) {
			throw new BusinessException("ADDITIONAL.ITEM.EXIST");
		}

	}
	private void checkIfExistAdditionalItemId(int id) {
		AdditionalItem currentAdditionalItem=this.additionalItemRepository.findById(id);
		if (currentAdditionalItem==null) {
			throw new BusinessException("INVALID.ADDITIONAL.ITEM.ID");
		}
	}
}
