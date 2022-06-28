package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.GetAllBrandsResponse;
import com.kodlamaio.rentACar.business.responses.brands.ReadBrandResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	 
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public BrandManager(BrandRepository brandRepository, ModelMapperService modelMapperService) {
	
		this.brandRepository = brandRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		
		checkIfBrandExistByName(createBrandRequest.getName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		brandRepository.save(brand);
		return new SuccessResult("BRAND.ADDED");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		
		checkIfExistBrandId(updateBrandRequest.getId());
		checkIfBrandExistByName(updateBrandRequest.getName());
		
		Brand brandToUpdate = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandRepository.save(brandToUpdate);
		return new SuccessResult("BRAND.UPDATED");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		
		checkIfExistBrandId(deleteBrandRequest.getId());
		
		brandRepository.deleteById(deleteBrandRequest.getId());
		return new SuccessResult("BRAND.DELETED");
	}

	@Override
	public DataResult<List<GetAllBrandsResponse>> getAll() {
		List<Brand> brands=this.brandRepository.findAll();
		List<GetAllBrandsResponse> response= brands.stream().map(brand->this.modelMapperService.forResponse().map(brand, GetAllBrandsResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllBrandsResponse>>(response);
	}

	@Override
	public DataResult<ReadBrandResponse> getById(int id) {
		
		checkIfExistBrandId(id);
		
		return new SuccessDataResult<ReadBrandResponse>(this.modelMapperService.forResponse().map(id, ReadBrandResponse.class),"BRAND.LISTED");
	}

	private void checkIfBrandExistByName(String name) {
		Brand currentBrand=this.brandRepository.findByName(name);
		if (currentBrand!=null) {
			throw new BusinessException("BRAND.EXIST");
		}
	}
	
	private void checkIfExistBrandId(int id) {
		Brand currentBrand=this.brandRepository.findById(id);
		if (currentBrand==null) {
			throw new BusinessException("INVALID.BRAND.ID");
		}
	}

	@Override
	public Brand getBrandById(int brandId) {
		checkIfExistBrandId(brandId);
		return brandRepository.findById(brandId);
	}
}
