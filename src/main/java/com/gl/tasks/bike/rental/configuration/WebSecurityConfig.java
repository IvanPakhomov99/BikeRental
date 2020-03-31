package com.gl.tasks.bike.rental.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.gl.tasks.bike.rental.enums.Role;
import com.gl.tasks.bike.rental.service.UserServiceImpl;
import com.gl.tasks.bike.rental.util.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    UserDetailsService userDetailsService = mongoUserDetails();
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
			.antMatchers("/api/auth/login").permitAll()
			.antMatchers("/api/auth/register").permitAll()
			.antMatchers(HttpMethod.GET, "/api/bike/**").permitAll()
			.antMatchers(HttpMethod.DELETE, "/api/bike/**").hasAuthority(Role.ADMIN.name())
			.antMatchers(HttpMethod.POST, "/api/bike/**").hasAuthority(Role.ADMIN.name())
			.antMatchers(HttpMethod.PUT, "/api/bike/**").hasAuthority(Role.ADMIN.name())
			.anyRequest().authenticated().and().csrf()
			.disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).and()
			.apply(new JwtConfigurer(jwtTokenProvider));
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
	    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
	            "Unauthorized");
	}

	@Bean
	public UserDetailsService mongoUserDetails() {
	    return new UserServiceImpl();
	}

}
