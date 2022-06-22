package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.business.requests.customers.CreateCustomerRequest;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisServiceAdapter implements PersonCheckService {

	@Override
	public boolean checkIfRealPerson(CreateCustomerRequest createCustomerRequest) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(createCustomerRequest.getNationalIdentification()),
				createCustomerRequest.getFirstName().toUpperCase(), createCustomerRequest.getLastName().toUpperCase(), createCustomerRequest.getBirthYear());


	}

}
