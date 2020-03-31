package com.gl.tasks.bike.rental.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gl.tasks.bike.rental.bean.User;
import com.gl.tasks.bike.rental.dto.UserDTO;
import com.gl.tasks.bike.rental.enums.Role;
import com.gl.tasks.bike.rental.repository.UserRepository;
import com.gl.tasks.bike.rental.util.DTOConverterUtil;
import com.gl.tasks.bike.rental.util.Translator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDTO upsert(UserDTO userDTO) {
		User user = User.builder()
					._id(userDTO.getId())
					.email(userDTO.getEmail())
					.password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
					.roles(userDTO.getRoles())
					.build();
		user = userRepository.save(user); 
		return DTOConverterUtil.convertToUserDTO(user);
	}

	@Override
	public UserDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user == null ? null :  DTOConverterUtil.convertToUserDTO(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

	    User user = userRepository.findByEmail(email);
	    if(user != null) {
	        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
	        return buildUserForAuthentication(user, authorities);
	    } else {
	        throw new UsernameNotFoundException(Translator.toLocale("username.not.found.exception"));
	    }
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    userRoles.forEach((role) -> {
	        roles.add(new SimpleGrantedAuthority(role.name()));
	    });

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
	    return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
