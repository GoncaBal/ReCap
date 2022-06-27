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
@Table(name = "corporateCustomers")
@PrimaryKeyJoinColumn(name = "corporateId", referencedColumnName = "customerId")

public class CorporateCustomer extends Customer {
	@Column(name = "corporateId", insertable = false, updatable = false)
	private int corporateId;
	@Column(name = "companyName")
	private String companyName;
	@Column(name = "taxNumber")
	private String taxNumber;

}
