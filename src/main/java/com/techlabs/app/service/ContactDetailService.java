package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.ContactDetailDTO;

public interface ContactDetailService {

	

	ContactDetailDTO createContactDetail(Long contactId, ContactDetailDTO contactDetailDTO);

	List<ContactDetailDTO> getAllContactDetails(Long contactId);

	ContactDetailDTO getContactDetailById(Long id);

	ContactDetailDTO updateContactDetail(Long id, ContactDetailDTO contactDetailDTO);

	void deleteContactDetail(Long id);

}
