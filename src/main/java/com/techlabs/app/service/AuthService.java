package com.techlabs.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.dto.UserDTO;

public interface AuthService {

	String login(LoginDto loginDto);

	//String register(UserDTO userDTO, String role);

	String register(RegisterDto userDTO, String newRole);

	//String register(RegisterDto userDTO, String newRole);
}
