package com.kodlamaio.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	Invoice findByInvoiceNumber(String invoiceNumber);
	Invoice findById(int id);
	Invoice findByRentalId(int invoiceId);
	
}
