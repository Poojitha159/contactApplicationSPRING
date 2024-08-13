package com.techlabs.app.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.ContactDTO;
import com.techlabs.app.dto.ContactDetailDTO;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetail;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.ContactNotFoundException;
import com.techlabs.app.exception.UserNotFoundException;
import com.techlabs.app.repository.ContactRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PagedResponse;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
	private UserRepository userRepository;
    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        // Ensure the user is associated
        if (contactDTO.getUser() != null && contactDTO.getUser().getId() != null) {
            User user = userRepository.findById(contactDTO.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id " + contactDTO.getUser().getId()));
            Contact contact = convertToContactEntity(contactDTO);
            contact.setUser(user); // Associate the user with the contact
            Contact savedContact = contactRepository.save(contact);
            return convertToContactDTO(savedContact);
        } else {
            throw new IllegalArgumentException("User ID must be provided");
        }
    }
    @Override
    public Optional<ContactDTO> getContactById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(this::convertToContactDTO);
    }

    @Override
    public PagedResponse<ContactDTO> getAllContacts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var contactsPage = contactRepository.findAll(pageable);

        var contacts = contactsPage.getContent().stream()
                .map(this::convertToContactDTO)
                .collect(Collectors.toList());

        return new PagedResponse<>(contacts, contactsPage.getNumber(), contactsPage.getSize(),
                contactsPage.getTotalElements(), contactsPage.getTotalPages(), contactsPage.isLast());
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));

        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setActive(contactDTO.isActive());

        if (contactDTO.getContactDetail() != null) {
            var contactDetails = contactDTO.getContactDetail().stream()
                    .map(this::convertToContactDetailEntity)
                    .collect(Collectors.toList());
            contact.setContactDetails(contactDetails);
        }

        Contact updatedContact = contactRepository.save(contact);
        return convertToContactDTO(updatedContact);
    }

    @Override
    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
        contact.setActive(false);
        contactRepository.save(contact);
        
        
    }

    // Utility methods to manually convert between DTOs and Entities

    private ContactDTO convertToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setActive(contact.isActive());
        contactDTO.setUser(contact.getUser());

        if (contact.getContactDetails() != null) {
            var contactDetailDTOs = contact.getContactDetails().stream()
                    .map(this::convertToContactDetailDTO)
                    .collect(Collectors.toList());
            contactDTO.setContactDetail(contactDetailDTOs);
        }

        return contactDTO;
    }

    private ContactDetailDTO convertToContactDetailDTO(ContactDetail contactDetail) {
        ContactDetailDTO contactDetailDTO = new ContactDetailDTO();
        contactDetailDTO.setId(contactDetail.getId());
        contactDetailDTO.setType(contactDetail.getType());
        contactDetailDTO.setValue(contactDetail.getValue());
        contactDetailDTO.setContactId(contactDetail.getContact().getId());
        return contactDetailDTO;
    }

    private Contact convertToContactEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setActive(true);

        if (contactDTO.getContactDetail() != null) {
            var contactDetails = contactDTO.getContactDetail().stream()
                    .map(this::convertToContactDetailEntity)
                    .collect(Collectors.toList());
            contact.setContactDetails(contactDetails);
        }

        return contact;
    }

    private ContactDetail convertToContactDetailEntity(ContactDetailDTO contactDetailDTO) {
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setId(contactDetailDTO.getId());
        contactDetail.setType(contactDetailDTO.getType());
        contactDetail.setValue(contactDetailDTO.getValue());
        return contactDetail;
    }

	
}