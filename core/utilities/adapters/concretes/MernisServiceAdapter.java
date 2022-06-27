package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.business.requests.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individualCustomers.UpdateIndividualCustomerRequest;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisServiceAdapter implements PersonCheckService {

	@Override
	public boolean checkIfRealPerson(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(createIndividualCustomerRequest.getNationalIdentification()),
				createIndividualCustomerRequest.getFirstName().toUpperCase(), createIndividualCustomerRequest.getLastName().toUpperCase(), createIndividualCustomerRequest.getBirthYear());


	}
	@Override
	public boolean checkIfRealPerson(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(updateIndividualCustomerRequest.getNationalIdentification()),
				updateIndividualCustomerRequest.getFirstName().toUpperCase(), updateIndividualCustomerRequest.getLastName().toUpperCase(), updateIndividualCustomerRequest.getBirthYear());


	}
	
}
