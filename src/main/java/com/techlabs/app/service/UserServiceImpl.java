package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.ContactDTO;
import com.techlabs.app.dto.ContactDetailDTO;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.dto.UserDTO;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetail;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.UserNotFoundException;
import com.techlabs.app.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	//@Autowired
	//private DTOConverter dtoConverter;

	@Override
	public UserDTO createUser(@Valid UserDTO userDTO) {
//		
		User user = new User();
        user.setUsername(userDTO.getUsername());
       // user.setPassword(userDTO.getPassword());
       // user.setRoles(userDTO.getRole());
        user.setIsActive(userDTO.getIsActive());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getContacts() != null) {
            List<Contact> contacts = userDTO.getContacts().stream()
                    .map(this::convertToContactEntity)
                    .collect(Collectors.toList());
            user.setContacts(contacts);
        }

        User savedUser = userRepository.save(user);
        return convertToUserDTO(savedUser);
	}

	@Override
	public List<UserDTO> getAllUsers() {
//		User user = userRepository.findByAll();
//		return user.stream().map(this::convertTODTO).collect(Collectors.toList());
		
		List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    
//		 return userRepository.findAll().stream()
//	                .map(this::convertToDTO)
//	                .collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("USER not found with id : " + id));
		return convertToUserDTO(user);
	}

	@Override
	public UserDTO updateUser(Long id, @Valid UserDTO userDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("USER not found with id : " + id));
		user.setUsername(userDTO.getUsername());
		//user.setRoles(userDTO.getRole());
//		if (userDTO.getPassword() != null) {
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        }

		user.setIsActive(user.getIsActive());
		user.setEmail(userDTO.getEmail());
		User updatedUser = userRepository.save(user);
		return convertToUserDTO(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("USER not found with id : " + id));
		user.setIsActive(false);
		userRepository.save(user);

	}

	private User convertToUser(RegisterDto registerDto) {
		User user=new User();
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
	//	user.setRoles(registerDto.getRole());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword())); 
		return user;	
	}
	
	private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
       // userDTO.setRole(user.getRoles());
      //  userDTO.setPassword(user.getPassword());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setEmail(user.getEmail());

        if (user.getContacts() != null) {
            List<ContactDTO> contactDTOs = user.getContacts().stream()
                    .map(this::convertToContactDTO)
                    .collect(Collectors.toList());
            userDTO.setContacts(contactDTOs);
        }

        return userDTO;
    }

    private ContactDTO convertToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setActive(contact.isActive());
        contactDTO.setUser(contact.getUser());
        contactDTO.setContactDetail(contact.getContactDetails().stream()
                .map(this::convertToContactDetailDTO)
                .collect(Collectors.toList()));
        return contactDTO;
    }

    private Contact convertToContactEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setActive(contactDTO.isActive());
        contact.setUser(contactDTO.getUser());
        contact.setContactDetails(contactDTO.getContactDetail().stream()
                .map(this::convertToContactDetailEntity)
                .collect(Collectors.toList()));
        return contact;
    }

    private ContactDetailDTO convertToContactDetailDTO(ContactDetail contactDetail) {
        ContactDetailDTO contactDetailDTO = new ContactDetailDTO();
        contactDetailDTO.setId(contactDetail.getId());
        contactDetailDTO.setType(contactDetail.getType());
        contactDetailDTO.setValue(contactDetail.getValue());
        return contactDetailDTO;
    }

    private ContactDetail convertToContactDetailEntity(ContactDetailDTO contactDetailDTO) {
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setId(contactDetailDTO.getId());
        contactDetail.setType(contactDetailDTO.getType());
        contactDetail.setValue(contactDetailDTO.getValue());
        return contactDetail;
    }

    

}
