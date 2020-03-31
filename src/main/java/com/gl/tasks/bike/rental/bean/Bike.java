package com.gl.tasks.bike.rental.bean;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Getter;

import com.gl.tasks.bike.rental.enums.BikeType;

@Builder
@Getter
@Document(collection = "bikes")
public final class Bike {
	
	@Id
	private String _id;
		
	private String name;

	private BikeType type;
	
	private BigDecimal price;
	
	public void update(String name, BikeType bikeType, BigDecimal price) {
		this.name = name;
		this.type = bikeType;
		this.price = price;
	}
}
