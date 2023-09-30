package com.mariahhau.events.Database.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mariahhau.events.Database.Documents.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long>{

    Optional<User> findUserByEmail(String email);
    
    Optional<User> findUserByUsername(String username);
}
