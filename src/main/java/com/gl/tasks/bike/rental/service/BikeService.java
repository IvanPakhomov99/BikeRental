package com.gl.tasks.bike.rental.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gl.tasks.bike.rental.dto.BikeDTO;
import com.gl.tasks.bike.rental.enums.BikeType;

@Service
public interface BikeService {
	
	BikeDTO upsert(BikeDTO bike);
	 
	BikeDTO delete(String id);
 
    List<BikeDTO> findAll();
 
    BikeDTO findById(String id);
 
    List<BikeDTO> findByName(String name);
    
    List<BikeDTO> findByType(BikeType type);
    
    List<BikeDTO> findByPriceBetween(BigDecimal min, BigDecimal max);
}
