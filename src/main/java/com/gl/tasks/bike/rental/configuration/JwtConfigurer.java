package com.gl.tasks.bike.rental.configuration;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gl.tasks.bike.rental.filter.JwtTokenFilter;
import com.gl.tasks.bike.rental.util.JwtTokenProvider;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	private JwtTokenProvider jwtTokenProvider;

	JwtConfigurer(JwtTokenProvider jwtTokenProvide) {
		this.jwtTokenProvider = jwtTokenProvide;
	}
	
	@Override
	public void configure(HttpSecurity builder) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
		builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
