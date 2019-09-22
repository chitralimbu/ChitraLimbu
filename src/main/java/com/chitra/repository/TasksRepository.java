package com.chitra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chitra.domain.Tasks;

public interface TasksRepository extends MongoRepository<Tasks, String>{

}
