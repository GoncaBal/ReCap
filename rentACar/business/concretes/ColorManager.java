package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.DeleteColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.ReadColorResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.ColorRepository;
import com.kodlamaio.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	ColorRepository colorRepository;
	
	@Autowired
	public ColorManager(ColorRepository colorRepository) {
		this.colorRepository = colorRepository;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		Color color=new Color();
		color.setName(createColorRequest.getName());
		this.colorRepository.save(color);
		return new SuccessResult("COLOR.ADDED");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		colorRepository.delete(colorRepository.findById(deleteColorRequest.getId()));
		return new SuccessResult("COLOR.DELETED");
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		Color colorToUpdate=colorRepository.findById(updateColorRequest.getId());
		colorToUpdate.setName(updateColorRequest.getName());
		colorRepository.save(colorToUpdate);
		return new SuccessResult("COLOR.UPDATED");
	}

	@Override
	public DataResult<List<Color>>  getAll() {
		
		return new SuccessDataResult<List<Color>>( colorRepository.findAll(),"COLOR.LISTED");
	}

	@Override
	public DataResult<Color> getById(ReadColorResponse readColorResponse) {
		return new SuccessDataResult<Color>(this.colorRepository.findById(readColorResponse.getId()));
	}

}
