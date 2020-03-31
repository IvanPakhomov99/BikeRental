package com.gl.tasks.bike.rental.enums;

import java.util.Arrays;

import com.gl.tasks.bike.rental.exceptions.TypeNotFoundException;

public enum BikeType {
	CLASSIC, ROAD, MOUNTAIN;
	
	public static BikeType getTypeByName(String name) {
		//TODO: filter find first change 
		return Arrays.stream(BikeType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst()
				.orElseThrow(() -> new TypeNotFoundException(name));
	}
}
