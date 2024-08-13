package com.techlabs.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
	
private Long id;
private String username;
//private String role;
//private String password;
private Boolean isActive;

private String email;
private List<ContactDTO> contacts; 

}

