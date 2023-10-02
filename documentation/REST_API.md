### GET 
__localhost:8080/events__ - Get all events from the database

Response: 200 OK
Body:

### GET
__localhost:8080/events/{id}__ - Get the information of a single event based on event id

Response: 200 OK
Body:


### POST
__localhost:8080/login__ - Log in with username and password.
Request: 
{
    "username": "",
    "password": ""
}

Response: 200 OK
Body:
{
    "accessToken": ""
}

### POST
__localhost:8080/signUp__ - Create a user account
Request: 
{
    "username": "",
    "email": "",
    "password": ""
}

Response: 201 Created
Body: User created successfully

### POST
__localhost:8080/events/{id}/register__ - Register for an event 
Requires an Authorization token (Bearer Token) ** OR ** an email address

If the client is not logged in (does not have a token), email address is required:
Request:
{
    "email":""
}

Response: 201 Created
Body: 

### POST
__localhost:8080/events/new__ - Create a new event (client must be logged in)
Requires an Authorization token (Bearer Token) 
Request:
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

Response: 201 Created
Body (example):
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

### POST
__localhost:8080/events/{id}/cancelRegistration__ - Cancel registration for an event (client must be logged in)
Requires an Authorization token (Bearer Token) 

No request body

Response: 200 OK
Registration cancelled successfully