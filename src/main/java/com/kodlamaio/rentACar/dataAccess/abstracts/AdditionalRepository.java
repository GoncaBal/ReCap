package com.kodlamaio.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.Additional;

public interface AdditionalRepository extends JpaRepository<Additional, Integer> {
	Additional getById(int id);
	Additional findByName(String name);
}
