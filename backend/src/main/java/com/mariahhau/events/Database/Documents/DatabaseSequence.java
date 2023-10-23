package com.mariahhau.events.Database.Documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "db_sequences") //This class represents each document in the "db_sequences" collection in MongoDB.
@Data
public class DatabaseSequence {

    @Id
    private String id;
    private long seq;
    
}
