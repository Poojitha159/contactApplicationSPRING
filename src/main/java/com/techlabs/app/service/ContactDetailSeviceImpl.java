package com.techlabs.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.ContactDetailDTO;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetail;
import com.techlabs.app.repository.ContactDetailRepository;
import com.techlabs.app.repository.ContactRepository;

@Service
public class ContactDetailSeviceImpl implements ContactDetailService{

	 @Autowired
	    private ContactDetailRepository contactDetailRepository;

	    @Autowired
	    private ContactRepository contactRepository;

	    @Override
	    public ContactDetailDTO createContactDetail(Long contactId, ContactDetailDTO contactDetailDTO) {
	        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new RuntimeException("Contact not found"));

	        ContactDetail contactDetail = new ContactDetail();
	        contactDetail.setType(contactDetailDTO.getType());
	        contactDetail.setValue(contactDetailDTO.getValue());
	        contactDetail.setContact(contact);

	        ContactDetail savedContactDetail = contactDetailRepository.save(contactDetail);
	        return mapToDTO(savedContactDetail);
	    }

	    @Override
	    public List<ContactDetailDTO> getAllContactDetails(Long contactId) {
	        List<ContactDetail> contactDetails = contactDetailRepository.findByContactId(contactId);
	        return contactDetails.stream().map(this::mapToDTO).collect(Collectors.toList());
	    }

	    @Override
	    public ContactDetailDTO getContactDetailById(Long id) {
	        ContactDetail contactDetail = contactDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Detail not found"));
	        return mapToDTO(contactDetail);
	    }

	    @Override
	    public ContactDetailDTO updateContactDetail(Long id, ContactDetailDTO contactDetailDTO) {
	        ContactDetail contactDetail = contactDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Detail not found"));

	        contactDetail.setType(contactDetailDTO.getType());
	        contactDetail.setValue(contactDetailDTO.getValue());

	        ContactDetail updatedContactDetail = contactDetailRepository.save(contactDetail);
	        return mapToDTO(updatedContactDetail);
	    }

	    @Override
	    public void deleteContactDetail(Long id) {
	        ContactDetail contactDetail = contactDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Detail not found"));
	        contactDetailRepository.delete(contactDetail);
	    }

	    private ContactDetailDTO mapToDTO(ContactDetail contactDetail) {
	        ContactDetailDTO dto = new ContactDetailDTO();
	        dto.setId(contactDetail.getId());
	        dto.setType(contactDetail.getType());
	        dto.setValue(contactDetail.getValue());
	        dto.setContactId(contactDetail.getContact().getId());
	        return dto;
	    }

}
