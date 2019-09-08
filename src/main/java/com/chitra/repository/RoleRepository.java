package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	Role findByRole(String role);
}
