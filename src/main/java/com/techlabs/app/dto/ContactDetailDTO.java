package com.techlabs.app.dto;

import lombok.Data;

@Data
public class ContactDetailDTO {
	private Long id;
	private String type;
	private String value;
    private Long contactId;  


}
