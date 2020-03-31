package com.gl.tasks.bike.rental.bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gl.tasks.bike.rental.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Document(collection = "users")
public class User {
	
	@Id
	private String _id;
	
	private String email;
	
	private String password;

    private Set<Role> roles;
    
    public static class UserBuilder {
        public UserBuilder roles(Set<Role> roles) {
        	if (roles == null) {
        		this.roles = new HashSet<>(Arrays.asList(Role.USER));
        	} else {
        		this.roles = roles;
        	}
        	return this;
        }
    }
}
