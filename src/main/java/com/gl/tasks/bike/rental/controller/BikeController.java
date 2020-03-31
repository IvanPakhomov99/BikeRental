package com.gl.tasks.bike.rental.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gl.tasks.bike.rental.dto.BikeDTO;
import com.gl.tasks.bike.rental.enums.BikeType;
import com.gl.tasks.bike.rental.service.BikeService;
import com.gl.tasks.bike.rental.util.Translator;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/bike")
@Slf4j
public class BikeController {
	
	private final BikeService bikeService;

	@Autowired
	public BikeController(BikeService bikeService) {
		this.bikeService = bikeService;
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	BikeDTO create(@RequestBody @Valid BikeDTO bikeDTO) {
		BikeDTO bike = bikeService.upsert(bikeDTO);
		log.info(Translator.toLocale("create.bike"), bike);
		return bike;
	}
	
	@DeleteMapping("/delete/{id}")
	BikeDTO delete(@PathVariable("id") String id) {
		BikeDTO bike = bikeService.delete(id);
		log.info(Translator.toLocale("delete.bike"), id);
		return bike;
	}
	
	@GetMapping("/get/{id}")
	BikeDTO findById(@PathVariable("id") String id) {
		BikeDTO bike = bikeService.findById(id);
		log.info(Translator.toLocale("bike.found"), bike);
		return bike;
	}
	
	@GetMapping("/get")
	List<BikeDTO> getAll(String testData) {
		List<BikeDTO> bikes = bikeService.findAll();
        log.info(Translator.toLocale("bikes.found"), bikes.size());
		return bikes;
	}
	
	@PutMapping("/update/{id}")
	BikeDTO update(@PathVariable("id") String id, @RequestBody @Valid BikeDTO bike) {
		bike.setId(id);
		BikeDTO bikeDTO = bikeService.upsert(bike);
        log.info(Translator.toLocale("update.bike"), bike, bikeDTO);
		return bike;
	}
	
	@GetMapping("/getByName/{name}")
	List<BikeDTO> getByName(@PathVariable("name") String name) {
		List<BikeDTO> bikes = bikeService.findByName(name);
        log.info(Translator.toLocale("bikes.found"), bikes.size());
		return bikes;
	}
	
	@GetMapping("/getByType/{type}")
	List<BikeDTO> getByType(@PathVariable("type") String typeName) {
		List<BikeDTO> bikes = bikeService.findByType(BikeType.getTypeByName(typeName));
        log.info(Translator.toLocale("bikes.found"), bikes.size());
		return bikes;
	}
	
	@GetMapping("/getByPrice")
	List<BikeDTO> getbyPrice(@RequestParam(value="min", required=true, defaultValue="0") BigDecimal min, 
			@RequestParam(value="max",  required=false) BigDecimal max) {
		List<BikeDTO> bikes = bikeService.findByPriceBetween(min, max);
        log.info(Translator.toLocale("bikes.found"), bikes.size());
		return bikes;
	}
}
