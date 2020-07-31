package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.Blog;

public interface BlogRepository extends MongoRepository<Blog, Integer>{
}
