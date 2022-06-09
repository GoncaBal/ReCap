package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.ReadBrandResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	private BrandRepository brandRepository;

	@Autowired // Bu anotasyonla repository'e gidip newleyip bana gönder demek.
	public BrandManager(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		// Mapping yapıldı.
		Brand brand = new Brand();
		brand.setName(createBrandRequest.getName());
		brandRepository.save(brand);
		return new SuccessResult("BRAND.ADDED");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Brand brandToUpdate =brandRepository.findById(updateBrandRequest.getId());
		brandToUpdate.setName(updateBrandRequest.getName());
		this.brandRepository.save(brandToUpdate);
		return new SuccessResult("BRAND.UPDATED");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		brandRepository.delete(brandRepository.findById(deleteBrandRequest.getId()));
		return new SuccessResult("BRAND.DELETED");
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(brandRepository.findAll(), "BRAND.LISTED");
	}

	
	@Override
	public DataResult<Brand> getById(ReadBrandResponse readBrandResponse) {

		return new SuccessDataResult<Brand>(this.brandRepository.findById(readBrandResponse.getId()), "BRAND.LISTED");
	}

}

//Bir markadan maksimum 5 araba olabilir.