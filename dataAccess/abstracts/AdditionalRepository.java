package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.Additional;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

public interface AdditionalRepository extends JpaRepository<Additional, Integer> {
	Additional getById(int id);
	List<Additional> getByRentalId(int id);
	List<AdditionalItem> getByAdditionalItemId(int id);
}
