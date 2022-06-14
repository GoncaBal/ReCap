package com.kodlamaio.rentACar.business.abstracts;

import java.rmi.RemoteException;

import com.kodlamaio.rentACar.business.requests.customers.CreateCustomerRequest;
import com.kodlamaio.rentACar.entities.concretes.Customer;

public interface PersonCheckService {
	boolean checkIfRealPerson(CreateCustomerRequest createCustomerRequest) throws NumberFormatException, RemoteException;
}
