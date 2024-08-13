package com.techlabs.app.service;

import java.util.Optional;

import com.techlabs.app.dto.ContactDTO;
import com.techlabs.app.util.PagedResponse;

public interface ContactService {


	ContactDTO createContact(ContactDTO contactDTO);

	Optional<ContactDTO> getContactById(Long id);

	PagedResponse<ContactDTO> getAllContacts(int page, int size);

	ContactDTO updateContact(Long id, ContactDTO contactDTO);

	void deleteContact(Long id);

}
