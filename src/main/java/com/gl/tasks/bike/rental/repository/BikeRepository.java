package com.gl.tasks.bike.rental.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gl.tasks.bike.rental.bean.Bike;
import com.gl.tasks.bike.rental.enums.BikeType;

public interface BikeRepository extends MongoRepository<Bike, String>{
    
	List<Bike> findByName(String name);
	
	List<Bike> findByType(BikeType type);
	
	List<Bike> findByPriceBetween(BigDecimal min, BigDecimal max);
}
