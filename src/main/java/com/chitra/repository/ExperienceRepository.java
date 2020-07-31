package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.Experience;

public interface ExperienceRepository extends MongoRepository<Experience, String>{
	Experience findByTitle(String title);
	Experience findByOrganisation(String org);
	Long removeByOrganisation(String org);
}
