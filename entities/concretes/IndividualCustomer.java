package com.kodlamaio.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "individualCustomers")
@PrimaryKeyJoinColumn(name = "individualId", referencedColumnName = "customerId")

public class IndividualCustomer extends Customer {

	@Column(name = "individualId", insertable = false, updatable = false)
	private int individualId;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "nationalIdentification")
	private String nationalIdentification;
	@Column(name = "birthYear")
	private int birthYear;


}
