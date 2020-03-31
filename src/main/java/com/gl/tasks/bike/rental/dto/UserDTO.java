package com.gl.tasks.bike.rental.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.gl.tasks.bike.rental.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	
	private String id;
	
	private @NotNull String email;
	
	private @NotNull String password;

    private Set<Role> roles;
	
}
