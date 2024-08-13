package com.techlabs.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetail;


@Repository
public interface ContactDetailRepository extends JpaRepository<ContactDetail,Long>{

	List<ContactDetail> findByContactId(Long contactId);

	Optional<ContactDetail> findById(Long id);

}
