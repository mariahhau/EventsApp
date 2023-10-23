package com.mariahhau.events.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mariahhau.events.Database.Documents.Event;

public interface IEventService {

    List<Event> allEvents();
    
    Optional<Event> singleEvent(long id);

    Integer registerForEvent(long eventId, long userId, String email);

    Integer registerForEvent(long eventId, String email);

    Integer cancelRegistrationForEvent(long eventId, long userId);
    
    Event createEvent(Map<String, String> payload, String username);


}