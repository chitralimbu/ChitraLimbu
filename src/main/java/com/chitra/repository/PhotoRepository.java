package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String>{
	
}
