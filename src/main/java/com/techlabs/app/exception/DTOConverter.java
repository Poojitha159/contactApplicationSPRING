package com.techlabs.app.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.techlabs.app.dto.UserDTO;
import com.techlabs.app.entity.User;

@Component
public class DTOConverter {
	  @Autowired
	    private PasswordEncoder passwordEncoder;

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
      //  userDTO.setPassword(user.getPassword());
     //   userDTO.setRole(user.getRole());
        userDTO.setIsActive(user.getIsActive());
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
     //  user.setRole(userDTO.getRole());
     // user.setPassword(userDTO.getPassword());
       //user.setPassword(passwordEncoder.encode(userDTO.getPassword())); 
        user.setIsActive(userDTO.getIsActive());
        //user.setRole(userDTO.getRole());
        return user;
    }
}