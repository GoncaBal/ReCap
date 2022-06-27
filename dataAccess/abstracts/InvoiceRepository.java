package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	Invoice findByInvoiceNumber(String invoiceNumber);
	List<AdditionalItem> findByRentalId(int id);
	Invoice findById(int id);
}
