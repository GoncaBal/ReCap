package com.kodlamaio.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customerId",referencedColumnName = "id")
public class Customer extends User{
	@Column(name="customerId",insertable = false,updatable = false)
	private int customerId;
	@Column(name="customerNumber")
	private String customerNumber;
	
	@OneToMany(mappedBy = "customer")
	private List<Rental> rentals;
	@OneToMany(mappedBy = "customer")
	private List<Address> addresses;
}
