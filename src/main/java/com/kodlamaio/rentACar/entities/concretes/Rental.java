package com.kodlamaio.rentACar.entities.concretes;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "pickUpDate")
	private Date pickUpDate;
	@Column(name = "returnDate")
	private Date returnDate;
	@Column(name="totalDays")
	private int totalDays;
	@Column(name="totalPrice")
	private double totalPrice;
	
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
	
	@OneToMany(mappedBy = "rental")
	private List<Additional> additionals;
	

	@ManyToOne
	@JoinColumn(name="pickUpCity_id")
	private City pickCity;
	
	@ManyToOne
	@JoinColumn(name="returnCity_id")
	private City returnCity;
}
