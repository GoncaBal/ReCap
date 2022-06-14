package com.kodlamaio.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer getById(int id);
	Customer findByeMail(String mail);
}
