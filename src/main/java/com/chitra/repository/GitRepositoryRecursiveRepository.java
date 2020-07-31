package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.GitRepositoryRecursive;

public interface GitRepositoryRecursiveRepository extends MongoRepository<GitRepositoryRecursive, String>{

}
