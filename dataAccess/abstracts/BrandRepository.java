package com.kodlamaio.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
	Brand getById(int id);
	Brand findByName(String name);
}
