package com.gl.tasks.bike.rental.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.gl.tasks.bike.rental.enums.BikeType;
import com.gl.tasks.bike.rental.validator.EnsureNumber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BikeDTO {
	
	private String id;
	
	private @NotNull String name;

	private @NotNull BikeType type;
	
	private @NotNull @EnsureNumber BigDecimal price;
}
