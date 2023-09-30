package com.mariahhau.events.Service;

import java.util.List;
import java.util.Optional;

import com.mariahhau.events.Database.Documents.User;

public interface IUserService {

    Integer saveUser(User user);

    Optional<User> findUserByEmail(String email);

    List<User> findAllUsers();
}
    
