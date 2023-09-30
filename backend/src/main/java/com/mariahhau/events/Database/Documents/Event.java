package com.mariahhau.events.Database.Documents;

import java.util.ArrayList;
//import java.time.LocalDate;
//import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;

@Document(collection = "events") //This class presents each document in the "events" collection in MongoDB
@Data //generates getters and setters
@AllArgsConstructor //generates a contructor that requires one argument for every field in the class 
@NoArgsConstructor //generates a contructor that has no arguments 
public class Event {

    /*SEQUENCE_NAME is a reference to an auto-incrementing sequence number that stored in db_sequences collection in MongoDB
    each event gets a unique sequence number that is used as an id */

    @Transient //Transient fields are ignored and not persisted in data store
    public static final String SEQUENCE_NAME = "events_sequence"; 

    @Id //specifies the primary key of an entity
    private long id; 
    
    @NonNull //Fields that are annotated with @NonNull result in null checks with the corresponding parameters in the AllArgsConstructor
    private String title; 
    private String description;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String organizer;
    private String location;
    private String image;
    private ArrayList<Participant> participants; 
    private List<String> unregParticipants; // participants that have registered for an event without logging in
    private int maxParticipants;

    //@NoArgsConstructor //TODO miksi ei toimi
    @AllArgsConstructor
    private class Participant {
        private Long id;
        private String email;

        public Long getId(){
            return id;

        }
        public String getEmail(){
            return email;
        }


        //TODO muuta tätä? tää palauttaa nyt true vaikka email olis eri että ilmoittautumisen voi perua pelkällä userId:llä 
        @Override
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }

            if (!(o instanceof Participant)) {
                return false;
            }

            Participant p = (Participant) o;

            if (this.getId() == p.getId()){
                return true;
            } else {
                return false;
            }
            


        }

    }

    public Event(String title){
        this.title = title;
        
    }

   
    public boolean addParticipant(long userId, String email){

        System.out.println("Event.addParticipant, userId:" + userId);

        if (participants == null) {
            participants = new ArrayList<Participant>();
        }
        
        if (maxParticipants >= 0 && this.getParticipantCount() >= maxParticipants) {
            System.out.println("maxparticipants ylittyi");
            return false;
        }
        if (checkIfEmailExists(email)) {
            return false;
        }
        System.out.println("tääl");
        participants.add(new Participant(userId, email));
         System.out.println("nyt tääl");
        return true;
    }

    //returns the Event object with updated participants list or an empty Optional if the participant was not found
    public Optional<Event> removeParticipant(long userId){
    //public Event removeParticipant(long userId){

        System.out.println("Event.removeParticipant, userId:" + userId);

        /*if (!checkIfUserIdExists(userId)) {
            System.out.println("User id was not found");
            return Optional.empty();

        }*/ //TODO tarviiko tätä tarkistusta olla erikseen
        System.out.println("tääl");
        Participant p = new Participant(userId, null);
        if (participants.remove(p)) {
            return Optional.of(this);

        }
          return Optional.empty();
        
        
        
    }


    public boolean addParticipant(String email){

        if (unregParticipants == null) {
            unregParticipants = new ArrayList<String>();
        }
        
        if (maxParticipants >= 0 && this.getParticipantCount() >= maxParticipants) {
            System.out.println("maxparticipants ylittyi");
            return false;
        }

        if (checkIfEmailExists(email)) {
            return false;
        }
      
        unregParticipants.add(email);
        return true;
    }

    public int getParticipantCount() {
        int count = 0;

        if (participants != null) {
            count += participants.size();
        }

        if (unregParticipants != null) {
            count += unregParticipants.size();
        }
        
        return count;

    }

    public boolean checkIfEmailExists(String email) {

        for (int i = 0; i < participants.size(); i++) { 
            String temp = participants.get(i).getEmail();
            if (temp != null && temp.equals(email)) { 
                System.out.println("email löytyi participants");
                return true;
            }
            
            
        }

        for (int i = 0; i < unregParticipants.size(); i++) { 
            String temp = unregParticipants.get(i);
            
            if (temp != null && temp.equals(email)){
                System.out.println("email löytyi unregparticipants");
                
                return true;
            }
            
        }
    
        
        return false;

    }


    public boolean checkIfUserIdExists(Long id) {

        for (int i = 0; i < participants.size(); i++) { 
            if (participants.get(i).getId() == id) {
                System.out.println("id löytyi");
                return true;
            }
        }
        return false;
    }
    

}
