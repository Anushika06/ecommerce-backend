package com.example.e_commerce_backend.repository;

import com.example.e_commerce_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User,String> {
}
