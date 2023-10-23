package com.mariahhau.events.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mariahhau.events.Database.Documents.User;
import com.mariahhau.events.Database.Repositories.UserRepository;

@Service 
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mt;

    @Override
    public List<User> findAllUsers() {
        System.out.println("UserService: findAllUsers");
        return userRepository.findAll();
    }
        
    @Override
    public Optional<User> findUserByEmail(String email) {
        System.out.println("UserService: findUserByEmail");
        return userRepository.findUserByEmail(email);
    }  

    public Optional<User> findUserByUsername(String username) {
        System.out.print("UserService: findUserByUsername:  " + username);
        System.out.println(userRepository.findUserByUsername(username).toString());
        return userRepository.findUserByUsername(username);
    }  

    
    @Override
    public Integer saveUser(User user) {
       System.out.println("UserService: saveUser");
       if (findUserByUsername(user.getUsername()).isPresent()){
        return 1;
       }
       SequenceGeneratorService sequenceGenerator = new SequenceGeneratorService(mt);
       user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
       userRepository.save(user);
       return 0;

    }

   

    
}
