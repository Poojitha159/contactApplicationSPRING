package com.techlabs.app.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.ContactDTO;
import com.techlabs.app.entity.User;
import com.techlabs.app.service.ContactService;
import com.techlabs.app.util.PagedResponse;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

	
    @Autowired
    private ContactService contactService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ContactDTO> createContactForUser(
            @PathVariable Long userId,
            @RequestBody ContactDTO contactDTO) {
    	logger.info("Create contact for user "+userId);
    	
        contactDTO.setUser(new User(userId)); // Set the user ID in the ContactDTO
        ContactDTO createdContact = contactService.createContact(contactDTO);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<PagedResponse<ContactDTO>> getAllContacts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
    	
    	logger.info("TO get ALL Contacts ");
        PagedResponse<ContactDTO> contacts = contactService.getAllContacts(page, size);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
    	
    	logger.info("TO get contact By ID");
    	Optional<ContactDTO> contactDTO = contactService.getContactById(id);
        return contactDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
    	logger.info("Updating contact of ID "+id);
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        if (updatedContact != null) {
            return ResponseEntity.ok(updatedContact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
    	logger.info("Deactivating the contact of "+id);
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}