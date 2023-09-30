package com.mariahhau.events.Database.Documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "users") //This class presents each document in the "users" collection in MongoDB
@Data //generates getters and setters
@AllArgsConstructor //generates a contructor that requires one argument for every field in the class
@NoArgsConstructor //generates a contructor that has no arguments 
public class User {

    /*SEQUENCE_NAME is a reference to an auto-incrementing sequence number that stored in db_sequences collection in MongoDB
    each user gets a unique sequence number that is used as an id */

    @Transient //Transient fields are ignored and not persisted in data store
    public static final String SEQUENCE_NAME = "users_sequence"; 

    @Id
    private Long id;

    @NonNull
    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private String role; //TODO lisää rooleja (lista)



}
