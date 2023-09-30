package com.mariahhau.events.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mariahhau.events.Database.Documents.Event;
import com.mariahhau.events.Database.Repositories.EventRepository;

//This class contains methods for event management (CRUD)
@Service //@Service annotation is used with classes that provide some business functionalities. 

public class EventService {

    @Autowired //Spring instantiates this automatically
    private EventRepository eventRepository;

    @Autowired
    private MongoTemplate mt;

    //Retrieve all events from database
    public List<Event> allEvents() {
        return eventRepository.findAll();

    }

    //Retrieve the information of a single event by id 
    public Optional<Event> singleEvent(long id) {
        System.out.println(id);
        return eventRepository.findEventById(id);
    }

     
    //Register for an event
    //tarkista max participants
    public Integer registerToEvent(long eventId, long userId, String email) throws NoSuchElementException {
        System.out.println("registerToEvent(long, long, string), userId" + userId  );
        
        if (!eventRepository.findEventById(eventId).isPresent()) {
            return -1;
        } else {
            
            Event event = eventRepository.findEventById(eventId).orElseThrow();
            
            
            if (!event.addParticipant(userId, email)) {
                return -1;
                
            };
            
            eventRepository.save(event);
        }
        return 0;
    }


    //Cancel registration for an event, returns 0 if cancelled successfully, else -1
    public Integer cancelRegistrationForEvent(long eventId, long userId) throws NoSuchElementException {
        System.out.println("Cancel registration userId: " + userId  );
        
        if (!eventRepository.findEventById(eventId).isPresent()) {
            return -1;
        } else {
            
            Event event = eventRepository.findEventById(eventId).orElseThrow();

            if (event.removeParticipant(userId).isPresent()) {
                
                System.out.println("remove participant EventService rivi 77 onko rikki");
                eventRepository.save(event);
                return 0;
                
            };
            
           
        }
        return -1;
    }

    public Integer registerToEvent(long eventId, String email) throws NoSuchElementException {
        System.out.println("registerToEvent(long, string)");
        
        if (!eventRepository.findEventById(eventId).isPresent()) {
            System.out.println("täälä");
            return -1;
        } else {
            System.out.println("rivi 95");
            Event event = eventRepository.findEventById(eventId).orElseThrow();
            
            if (!event.addParticipant(email)) {
                return -1;
                
            };
            eventRepository.save(event);
        }
        return 0;
    }


    /**
     * Saves a new event to database
     * @param Event information in JSON format 
     * example:
     * {
        "title": "Birthday Party",
        "description": "Fun party yay",
        "startDate": "2023-09-18",
        "endDate": "2023-09-18",
        "startTime": "18:00",
        "endTime": "22:00",
        "location": "Teekkaritalo",
        "image": "url?",
        "maxParticipants": 20
    },
     * @return the new Event object
     */
    
    public Event createEvent(Map<String, String> payload, String username) {
        System.out.println("Create event");


        Event event = new Event();
        
        SequenceGeneratorService sequenceGenerator = new SequenceGeneratorService(mt);
        
        event.setId(sequenceGenerator.generateSequence(Event.SEQUENCE_NAME));

        if (payload.get("title") != null) {
            event.setTitle(payload.get("title"));
        } else {
            event.setTitle("Tapahtuma");
        }
        
        if (payload.get("description") != null) {
            event.setDescription(payload.get(("description") ));
        } else {
            event.setDescription("");
        }

        if (payload.get("startDate") != null) {
            event.setStartDate(payload.get(("startDate") ));
        } else {
            event.setStartDate(LocalDate.now().toString()); //sets the default start date to today
        }

        if (payload.get("endDate") != null) {
            event.setEndDate(payload.get(("endDate") ));
        } else {
            event.setEndDate(LocalDate.now().toString()); //sets the default end date to today
        }

        if (payload.get("startTime") != null) {
            event.setStartTime(payload.get(("startTime") ));
        } else {
           event.setStartTime(LocalTime.now().toString()); //TODO: Onko tässä järkeääää; sets the default start time to now  
        }

        if (payload.get("endTime") != null) {
            event.setEndTime(payload.get(("endTime") ));
        } else {
           event.setEndTime(LocalTime.now().plusHours(1).toString()); //TODO: Onko tässäkään järkeääää; sets the default start time to exactly 1 hour from now
        }

        if (payload.get("location") != null) {
            event.setLocation(payload.get(("location")));
        } else {
           event.setLocation(""); 
        }

        if (payload.get("maxParticipants") != null) {
            try{
                int number = Integer.parseInt(payload.get("maxParticipants"));
                event.setMaxParticipants(number);
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                event.setMaxParticipants(-1);
            }

        } else {
            event.setMaxParticipants(-1);//sets the default maximum number of participants to -1 (meaning there is no limit)
        }
 
        event.setOrganizer(username);
        //event.setParticipants(new ArrayList<Map<Long, String>>()); //TODO: ?
      

        eventRepository.save(event);

        return event;
    }

    
    
    
}
