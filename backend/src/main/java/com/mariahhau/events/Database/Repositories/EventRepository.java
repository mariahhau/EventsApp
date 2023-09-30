package com.mariahhau.events.Database.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mariahhau.events.Database.Documents.Event;

@Repository // annotates classes at the persistence layer, which will act as a database repository
public interface EventRepository extends MongoRepository<Event, Long>{

    Optional<Event> findEventById(long id);


    
}
