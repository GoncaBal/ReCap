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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "additionals" })

@Table(name = "additionals")
public class Additional {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "pickUpDate")
	private Date pickUpDate;
	@Column(name = "returnDate")
	private Date returnDate;
	@Column(name = "totalDays")
	private int totalDays;
	@Column(name = "totalPrice")
	private double totalPrice;
	@ManyToOne
	@JoinColumn(name = "additionalItem_id")
	private AdditionalItem additionalItem;
	@ManyToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;
	

}
