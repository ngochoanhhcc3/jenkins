package com.example.jwt2.javainuse.service;

import com.example.jwt2.javainuse.entity.UserDto;
import com.example.jwt2.javainuse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.example.jwt2.javainuse.entity.UserDto user = userRepository.findByUsernameDTO(username);

		List<SimpleGrantedAuthority> lsAuth = new ArrayList<>();
		lsAuth.add(new SimpleGrantedAuthority(user.getRole()));

		if (user.getUserName().equals(username)) {
			return new User(user.getUserName(), user.getPassword(), lsAuth);
		} else {
			throw new UsernameNotFoundException("Not found : " + username);
		}

	}

}