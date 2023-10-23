package com.mariahhau.events.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mariahhau.events.Database.Documents.DatabaseSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;
//This class is used to generate automatically incrementing sequence numbers (id) for events.
//Source: https://github.com/eugenp/tutorials/blob/master/persistence-modules/spring-boot-persistence-mongodb/src/main/java/com/baeldung/mongodb/services/SequenceGeneratorService.java
@Service
public class SequenceGeneratorService {
    
    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
        new Update().inc("seq", 1), options().returnNew(true).upsert(true),
        DatabaseSequence.class);
        
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
    
}
