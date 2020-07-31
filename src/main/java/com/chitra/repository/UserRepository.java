package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.User;


public interface UserRepository extends MongoRepository<User, Integer>{

	User findByUsername(String username);
	
}
