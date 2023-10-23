**1. GET localhost:8080/api/events** - Get all events from the database

Response: 200 OK  
Response body example:
```
[
    {
        "id": 17,
        "title": "Bilbo's Birthday",
        "description": "party with food, fireworks, dancing and much merriment",
        "startDate": "2023-09-22",
        "endDate": "2023-09-22",
        "startTime": "17:00",
        "endTime": "23:00",
        "organizer": "Bilbo",
        "location": "Shire",
        "image": null,
        "participants": [
            {
                "id": 13,
                "email": "frodo@test.fi"
            }
        ],
        "unregParticipants": [
            "gandalf@test.fi"
        ],
        "maxParticipants": 100,
        "participantCount": 2
    },
        "id": 18,
        "title": "Movie night",
        "description": "LOTR maraton",
        "startDate": "2023-10-27",
        "endDate": "2023-10-28",
        "startTime": "15:00",
        "endTime": "02:00",
        "organizer": "Smeagol",
        "location": "Living room",
        "image": null,
        "participants": null,
        "unregParticipants": null,
        "maxParticipants": 6,
        "participantCount": 0
    }
]
```


</br></br></br>
**2. POST localhost:8080/api/events** - Create a new event (client must be logged in)  

Requires an Authorization token (Bearer Token)   

Request body example:  
```
{   
    "title": "Movie night",
    "description": "LOTR maraton",
    "startDate": "2023-10-27",
    "endDate": "2023-10-28",
    "startTime": "15:00",
    "endTime": "02:00",
    "location": "Living room",
    "maxParticipants": 6

}  
```


</br>
Response: 201 Created  

Response body example:  
```
{  
    "id": 18,
    "title": "Movie night",
    "description": "LOTR maraton",
    "startDate": "2023-10-27",
    "endDate": "2023-10-28",
    "startTime": "15:00",
    "endTime": "02:00",
    "organizer": "Smeagol",
    "location": "Living room",
    "image": null,
    "participants": null,
    "unregParticipants": null,
    "maxParticipants": 6,
    "participantCount": 0

}
```

</br></br></br>
**3. GET localhost:8080/api/events/{id}**  - Get the information of a single event based on event id

Response: 200 OK  
Response body example: 
```
{  
    "id": 18,
    "title": "Movie night",
    "description": "LOTR maraton",
    "startDate": "2023-10-27",
    "endDate": "2023-10-28",
    "startTime": "15:00",
    "endTime": "02:00",
    "organizer": "Smeagol",
    "location": "Living room",
    "image": null,
    "participants": null,
    "unregParticipants": null,
    "maxParticipants": 6,
    "participantCount": 0

}
```

</br></br></br>

**4. POST localhost:8080/api/events/{id}** - Register for an event   

Requires an Authorization token (Bearer Token) **OR** an email address  
</br>
If the client is not logged in (does not have a token), a request body is required, for example:  
```
{  

    "email":"test@test.com"  

}
```
</br>
Response: 201 Created</br>  
Response body: Registration was successful


</br></br></br>
**5. DELETE localhost:8080/api/events/{id}** - Cancel registration for an event (client must be logged in)  

Requires an Authorization token (Bearer Token)   

No request body  


Response: 200 OK</br>
Response body: Registration cancelled successfully  

</br></br></br>
**6. POST localhost:8080/api/login** - Log in with username and password.  

</br>
Request body:

```
{    

    "username": "",  

    "password": ""    

}
```
</br></br>
Response: 200 OK</br>
Response body:
```
{    
    "accessToken": ""    

}
```
  
</br></br></br>
**7. POST localhost:8080/api/signUp** - Create a user account  

Request body:  
```
{  

    "username": "",
    "email": "",
    "password": ""  

}  
``` 


Response: 201 Created  </br>
Response body: User created successfully  
  
</br></br></br>

