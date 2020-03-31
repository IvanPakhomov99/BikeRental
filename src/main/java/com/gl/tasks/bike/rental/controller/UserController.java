package com.gl.tasks.bike.rental.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.tasks.bike.rental.dto.UserDTO;
import com.gl.tasks.bike.rental.service.UserService;
import com.gl.tasks.bike.rental.util.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;
    
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO data) {
		try {
			String username = data.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, userService.findByEmail(username).getRoles());
            Map<String, String> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }
	
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDTO user) {
        UserDTO userExists = userService.findByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.upsert(user);
        Map<String, String> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }
}
