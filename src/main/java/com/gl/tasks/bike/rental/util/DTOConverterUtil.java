package com.gl.tasks.bike.rental.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gl.tasks.bike.rental.bean.Bike;
import com.gl.tasks.bike.rental.bean.User;
import com.gl.tasks.bike.rental.dto.BikeDTO;
import com.gl.tasks.bike.rental.dto.UserDTO;

@Component
public final class DTOConverterUtil {

	public static BikeDTO convertToBikeDTO(final Bike bike) {
		return new BikeDTO(bike.get_id(), bike.getName(), bike.getType(), bike.getPrice());
	}
	
	public static List<BikeDTO> convertToBikeDTOs(final List<Bike> bikes) {
		return bikes.stream()
			.map(DTOConverterUtil::convertToBikeDTO)
			.collect(Collectors.toList());
	}
	
	public static UserDTO convertToUserDTO(final User bike) {
		return new UserDTO(bike.get_id(), bike.getEmail(), bike.getPassword(), bike.getRoles());
	}
	
	public static List<UserDTO> convertToUserDTOs(final List<User> bikes) {
		return bikes.stream()
			.map(DTOConverterUtil::convertToUserDTO)
			.collect(Collectors.toList());
	}
}
