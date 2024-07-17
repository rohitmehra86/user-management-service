package com.paxaris.usermanagement.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paxaris.usermanagement.tenant.entity.User;

/**
 * @author Rohit Mehra
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String userName);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);

}
