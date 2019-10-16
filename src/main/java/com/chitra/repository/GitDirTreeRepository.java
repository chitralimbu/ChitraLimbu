package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.GitDirTree;

public interface GitDirTreeRepository extends MongoRepository<GitDirTree, String>{
}
