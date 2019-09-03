package com.chitra.repository;

import com.chitra.domain.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, Integer>{

}
