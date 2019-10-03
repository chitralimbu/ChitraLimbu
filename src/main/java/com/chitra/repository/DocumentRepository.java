package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.media.domain.Documents;

public interface DocumentRepository extends MongoRepository<Documents, String>{
	Documents findByTitle(String title);
}
