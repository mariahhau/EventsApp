**GET localhost:8080/events** - Get all events from the database

Response: 200 OK  
Response body:  ...

</br></br></br>
**GET localhost:8080/events/{id}**  - Get the information of a single event based on event id

Response: 200 OK  
Response body: ...

</br></br></br>
**POST localhost:8080/login** - Log in with username and password.  

</br>
Request body:

```
{    

    "username": "",  

    "password": ""    

}
```
</br></br>
Response: 200 OK   
Response body:
```
{    
    "accessToken": ""    

}
```
  
</br></br></br>
**POST localhost:8080/signUp** - Create a user account  

Request body:  
```
{  

    "username": "",
    "email": "",
    "password": ""  

}  
``` 


Response: 201 Created  </br>
User created successfully  
  
</br></br></br>
**POST localhost:8080/events/{id}/register** - Register for an event   

Requires an Authorization token (Bearer Token) **OR** an email address  
</br>
If the client is not logged in (does not have a token), a request body is required:  
```
{  

    "email":""  

}
```
</br>
Response: 201 Created  
Response body:   ...

</br></br></br>
**POST localhost:8080/events/new** - Create a new event (client must be logged in)  

Requires an Authorization token (Bearer Token)   

Request body:  
```
{  

    "title": "",
    "description": "",
    "startDate": "YYYY-MM-DD",
    "endDate": "YYYY-MM-DD",
    "startTime": "HH:MM",
    "endTime": "HH:MM",
    "location": "",
    "image": "image url?",
    "maxParticipants": int  

}  
```


</br>
Response: 201 Created  

Response body (example):  
```
{  

    "id": 9,
    "title": "Halloween",
    "description": "Halloween party :D",
    "startDate": "2023-10-02",
    "endDate": "2023-10-02",
    "startTime": "12:03:04.299160300",
    "endTime": "13:03:04.299160300",
    "organizer": "tirppa",
    "location": "",
    "image": null,
    "participants": null,
    "unregParticipants": null,
    "maxParticipants": -1,
    "participantCount": 0  

}
```
</br></br></br>
**POST localhost:8080/events/{id}/cancelRegistration** - Cancel registration for an event (client must be logged in)  

Requires an Authorization token (Bearer Token)   

No request body  


Response: 200 OK

Registration cancelled successfully  
