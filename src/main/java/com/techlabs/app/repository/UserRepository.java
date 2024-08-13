package com.techlabs.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//	Optional<User> findByUsername(String username);
//
//	boolean existsByEmail(String email);
//
//	boolean existsByUsername(String username);
//
//	Object findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);
//
	
	
	Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    // Corrected method signature to return Optional<User>
    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);
}
