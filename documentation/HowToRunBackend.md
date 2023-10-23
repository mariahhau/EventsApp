First create  **.env** file in backend/src/main/resources directory. Use [.env.example](https://github.com/mariahhau/EventsApp/blob/main/backend/src/main/resources/.env.example) as a template. </br>
You need a MongoDB Atlas account and a database to connect to. [Instructions for MongoDB Atlas](https://www.mongodb.com/docs/atlas/getting-started/)</br>

Running the app from command line (Windows)
1. cd to \EventsApp\backend
2. run ```mvn compile```
3. run ```mvn package```
4. cd to \EventsApp\backend\target
5. run ```java -jar events-0.0.1-SNAPSHOT.jar```
6. Now the backend is running on http://localhost:8080/
