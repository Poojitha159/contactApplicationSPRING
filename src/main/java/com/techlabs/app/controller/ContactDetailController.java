package com.techlabs.app.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.ContactDetailDTO;
import com.techlabs.app.service.ContactDetailService;

@RestController
@RequestMapping("/api/contact-detail")
public class ContactDetailController {
	
	private static final Logger logger=LoggerFactory.getLogger(ContactDetailController.class);

	@Autowired
    private ContactDetailService contactDetailService;

    @PostMapping("/{contactId}/details")
    public ResponseEntity<ContactDetailDTO> createContactDetail(
            @PathVariable Long contactId,
            @RequestBody ContactDetailDTO contactDetailDTO) {
    	
    	logger.info("Creating Details for contact");
        ContactDetailDTO createdDetail = contactDetailService.createContactDetail(contactId, contactDetailDTO);
        return ResponseEntity.ok(createdDetail);
    }

    @GetMapping("/{contactId}/details")
    public ResponseEntity<List<ContactDetailDTO>> getAllContactDetails(@PathVariable Long contactId) {
    	logger.info("to get all ContactDetails");
        List<ContactDetailDTO> contactDetails = contactDetailService.getAllContactDetails(contactId);
        return ResponseEntity.ok(contactDetails);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ContactDetailDTO> getContactDetailById(@PathVariable Long id) {
    	
    	logger.info("To get Contact details by  this id"+id);
        ContactDetailDTO contactDetail = contactDetailService.getContactDetailById(id);
        return ResponseEntity.ok(contactDetail);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<ContactDetailDTO> updateContactDetail(
            @PathVariable Long id,
            @RequestBody ContactDetailDTO contactDetailDTO) {
    	
    	logger.info("to update the contact details of id "+id);
        ContactDetailDTO updatedDetail = contactDetailService.updateContactDetail(id, contactDetailDTO);
        return ResponseEntity.ok(updatedDetail);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<Void> deleteContactDetail(@PathVariable Long id) {
    	
    	logger.info("to deactivate the Contact detail");
        contactDetailService.deleteContactDetail(id);
        return ResponseEntity.noContent().build();
    }

}
