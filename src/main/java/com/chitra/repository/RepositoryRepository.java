package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.Repository;

public interface RepositoryRepository extends MongoRepository<Repository, String>{
	public Repository findByName(String name);
}
