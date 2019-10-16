package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.GitObject;

public interface GitObjectRepository extends MongoRepository<GitObject, String>{

}
