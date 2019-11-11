package com.chitra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.GitRepository;

public interface GitRepositoryRepository extends MongoRepository<GitRepository, String>{
	GitRepository findByName(String name);
	Page<GitRepository> findAll(Pageable pageable);
}
