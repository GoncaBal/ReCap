package com.kodlamaio.rentACar.entities.concretes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoices")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="invoiceNumber")
	private String invoiceNumber;
	@Column(name="invoiceDate")
	private Date invoiceDate;
	@Column(name="state")
	private int state;
	@Column(name="TotalPrice")
	private double TotalPrice;
	@ManyToOne
	@JoinColumn(name="rental_id")
	private Rental rental;
	

	
	
	
}
