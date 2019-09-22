package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.media.domain.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String>{
	Photo findByTitle(String title);
}
