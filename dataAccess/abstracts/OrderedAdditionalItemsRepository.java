package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItems;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

public interface OrderedAdditionalItemsRepository extends JpaRepository<OrderedAdditionalItems, Integer> {
	
	List<OrderedAdditionalItems> findByRentalId(int rentalId);
	List<AdditionalItem> findByAdditionalItemId(int id);
	OrderedAdditionalItems findById(int id);
}
