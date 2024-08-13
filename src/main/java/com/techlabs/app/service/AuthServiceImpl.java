package com.techlabs.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.dto.UserDTO;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.ApiException;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private RoleRepository roleRepository;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.roleRepository = roleRepository;
	}

	@Override
	public String login(LoginDto loginDto) {

		System.out.println("areyyayyy");

		Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsernameOrEmail());

		if (byUsername.isEmpty() || byUsername.get().getIsActive() == false) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "User doest not exists!.");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto userDTO, String newRole) {

		if (userRepository.existsByUsername(userDTO.getUsername())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		//user.setIsActive(userDTO.getIsActive());
	//	user.setRole(userDTO.getRole());
		user.setPassword(userDTO.getPassword());

		// user.setContacts(userDTO.getContacts());

		user.setIsAdmin(false);

		if ("ROLE_ADMIN".equalsIgnoreCase(newRole)) {
			user.setIsAdmin(true);
		}
		user.setIsActive(true);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(newRole).get();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);

		// customer.setUser(user);
		// customerRepository.save(customer);

		return "User registered successfully!.";
	}
	

	

}
