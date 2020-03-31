package com.gl.tasks.bike.rental.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.gl.tasks.bike.rental.dto.UserDTO;

@Service
public interface UserService extends UserDetailsService{
	
	UserDTO upsert(UserDTO userDTO);
	
	UserDTO findByEmail(String email);
}
