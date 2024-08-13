package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.UserDTO;

import jakarta.validation.Valid;

public interface UserService {

	UserDTO createUser(@Valid UserDTO userDTO);

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long id);

	UserDTO updateUser(Long id, @Valid UserDTO userDTO);

	void deleteUser(Long id);

}
