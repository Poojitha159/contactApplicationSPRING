package com.techlabs.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.techlabs.app.entity.ContactDetail;
import com.techlabs.app.entity.User;

import lombok.Data;

@Data
public class ContactDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private boolean isActive;
	private User user;
	private List<ContactDetailDTO> contactDetail= new ArrayList<>();;

}
