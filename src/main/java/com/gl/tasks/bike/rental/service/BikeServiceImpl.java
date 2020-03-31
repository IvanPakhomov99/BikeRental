package com.gl.tasks.bike.rental.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.tasks.bike.rental.bean.Bike;
import com.gl.tasks.bike.rental.dto.BikeDTO;
import com.gl.tasks.bike.rental.enums.BikeType;
import com.gl.tasks.bike.rental.exceptions.BikeNotFoundException;
import com.gl.tasks.bike.rental.repository.BikeRepository;
import com.gl.tasks.bike.rental.util.DTOConverterUtil;

@Service
public class BikeServiceImpl implements BikeService{
	
	private final BikeRepository bikeRepository;
	
	@Autowired
	BikeServiceImpl(BikeRepository bikeRepository) {
		this.bikeRepository = bikeRepository;
	}
	
	@Override
	public BikeDTO upsert(BikeDTO bikeDTO) {
		Bike bike = Bike.builder()
					._id(bikeDTO.getId())
					.name(bikeDTO.getName())
					.type(bikeDTO.getType())
					.price(bikeDTO.getPrice())
					.build();
		bike = bikeRepository.save(bike);
		return DTOConverterUtil.convertToBikeDTO(bike);
	}

	@Override
	public BikeDTO delete(String id) {
		Bike bike = findBikeById(id);
		bikeRepository.delete(bike);
		return DTOConverterUtil.convertToBikeDTO(bike);
	}

	@Override
	public List<BikeDTO> findAll() {
		return DTOConverterUtil.convertToBikeDTOs(bikeRepository.findAll());
	}

	@Override
	public BikeDTO findById(String id) {
		return DTOConverterUtil.convertToBikeDTO(findBikeById(id));
	}

	@Override
	public List<BikeDTO> findByName(String name) {
		return DTOConverterUtil.convertToBikeDTOs(bikeRepository.findByName(name));
	}

	@Override
	public List<BikeDTO> findByType(BikeType type) {
		return DTOConverterUtil.convertToBikeDTOs(bikeRepository.findByType(type));

	}
	
	@Override
	public List<BikeDTO> findByPriceBetween(BigDecimal min, BigDecimal max) {
		if (max == null) {
			max = BigDecimal.valueOf(Integer.MAX_VALUE);
		}
		List<Bike> bikes = bikeRepository.findByPriceBetween(min, max);
		return DTOConverterUtil.convertToBikeDTOs(bikes);
	}
	
	private Bike findBikeById(String id) {
		return bikeRepository.findById(id)
			.orElseThrow(() -> new BikeNotFoundException(id));
	}
}
