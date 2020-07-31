package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.GitRepositoryContents;

public interface GitRepositoryContentsRepository extends MongoRepository<GitRepositoryContents, String>{

}
