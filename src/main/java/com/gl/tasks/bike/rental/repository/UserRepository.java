package com.gl.tasks.bike.rental.repository;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gl.tasks.bike.rental.bean.User;
import com.gl.tasks.bike.rental.enums.Role;

public interface UserRepository extends MongoRepository<User, String>{
	
	User findByEmail(String email);
	
	User findByRoles(Set<Role> role);
}
