package com.mariahhau.events.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mariahhau.events.Utilities;
import com.mariahhau.events.Database.Documents.Event;
import com.mariahhau.events.Security.UserPrincipal;
import com.mariahhau.events.Service.EventService;

@RestController // Marks this class as a request handler (@RestController combines @Controller and @ResponseBody)

@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<List<Event>>(eventService.allEvents(), HttpStatus.OK);
    }

    //Get one event by id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> getSingleEvent(@PathVariable long id) {
        return new ResponseEntity<Optional<Event>>(eventService.singleEvent(id), HttpStatus.OK);

    }

    //@AuthenticationPrincipal annotation injects the currently authenticated user's UserDetails into the method
    //Test logging in
    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) return "UserPrincipal was null";
        return "You are logged in as user " + principal.getUsername() + " User ID: " + principal.getUserId();

    }


    //Create new event
    @PostMapping("") //?
    public ResponseEntity<Event> createEvent(@RequestBody Map<String, String> payload, @AuthenticationPrincipal UserPrincipal principal) {
         return new ResponseEntity<Event>(eventService.createEvent(payload, principal.getUsername()), HttpStatus.CREATED);
        
    }

    


    //Register for an event
    @PostMapping("/{id}")
    public ResponseEntity<String> registerToEvent(@PathVariable long id, @AuthenticationPrincipal UserPrincipal principal, @RequestBody Optional<Map<String, String>> payload) {     

        if (principal == null){
            System.out.println("principal was null \n\n");

            if (payload != null) {
                if (payload.get().get("email") != null ) {
                    String email = payload.get().get("email");
                    if (Utilities.validateEmail(email)) {
                        System.out.println("Valid email");
                        if (eventService.registerToEvent(id, email) == 0){
                            System.out.println("Registration with email was successfullll");
                              return new ResponseEntity<String>("Registration was successful", HttpStatus.CREATED);

                        }

                    }
                   
                }
            }
            return new ResponseEntity<String>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
             System.out.println(principal.toString());

         if (eventService.registerToEvent(id, principal.getUserId(), principal.getEmail()) == 0) {
            
            return new ResponseEntity<String>("Registration was successful", HttpStatus.CREATED);
        } else return new ResponseEntity<String>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelRegistrationForEvent(@PathVariable long id, @AuthenticationPrincipal UserPrincipal principal) {     

        if (principal == null){
            System.out.println("principal was null \n\n");

            return new ResponseEntity<String>("Cancellation failed", HttpStatus.INTERNAL_SERVER_ERROR);

        } else {

            if (eventService.cancelRegistrationForEvent(id, principal.getUserId()) == 0) {
            
            return new ResponseEntity<String>("Registration cancelled successfully", HttpStatus.OK);
        } else return new ResponseEntity<String>("Cancellation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("/{id}/cancelRegistration")
    public ResponseEntity<String> cancelRegistrationForEvent(@PathVariable long id, @AuthenticationPrincipal UserPrincipal principal) {     

        if (principal == null){
            System.out.println("principal was null \n\n");

            return new ResponseEntity<String>("Cancellation failed", HttpStatus.INTERNAL_SERVER_ERROR);

        } else {

            if (eventService.cancelRegistrationForEvent(id, principal.getUserId()) == 0) {
            
            return new ResponseEntity<String>("Registration cancelled successfully", HttpStatus.OK);
        } else return new ResponseEntity<String>("Cancellation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    
    
}
