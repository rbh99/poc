# API Challenge

Design and create a RESTful API that could be used to manage a list of dog images. 

# Requirements

The operations we expect to see would be:

* List all of the available dog pictures grouped by breed
* List all of the available dog pictures of a particular breed
* Vote up and down a dog picture
* The details associated with a dog picture

The information the Dog Breed App needs to function is:

* A URL to a dog picture
* The number of votes a picture has received
* The dog's breed
* Any other information required to identify a specific dog

The Dog Breed App expects the response to be sorted by the number of votes a picture has received.

The API responses must be in JSON.

## Additional Voting Requirements

Each client is allowed to vote once for any particular dog picture.

# Setup

We provide you with a starter Spring Boot project. The project is already configured go use Spring MVC and talks to an 
in memory HSQLDB to store the results. Jackson is already included to provide JSON serialization and deserialization.

The Spring Boot Started Test module is included to facilitate both unit and integration tests.

Feel free to pick a different framework if you feel more comfortable doing so.

## Dependencies

- Maven
- Java 8

## Checking Out the Project

```
$ git clone https://gitlab.com/coding_challenge_backend/api_challenge
```

## Building

```
$ mvn package
```

## Running

You can run the app through Maven:

```
$ mvn spring-boot:run
```

or you can run the jar file from the build:

```
$ java -jar target/api_interview-0.1.0.jar
```

# Build, Deployment and Running

While this falls outside of the challenge, please consider the following as you get ready to discuss your solution with the team:

* How would you package this for deployment?
* How and where would you deploy this app?
* How can you tell that the app is up and running?
* How would you configure the app as it goes from dev, to qa and finally to production?
* How would you insulate the app from a downstream API if it had one.

# Submitting the Project

Please send us a zip file of your challenge when completed.

# Duration

You will have until the end of the day to complete the challenge. In our tests, we found it to takes about 4-6 hours to build.
 We encourage you to spend any leftover time to polish/document/test your app.
 
 
 
# Exposed API

 - to create a client (user)
	POST http://localhost:8080/clients/username/aa  (curl --header "Content-Type: application/json"  --request POST  http://localhost:8080/clients/username/aa)

 - to vote:  
 	curl --header "Content-Type: application/json" --request POST --data '{"clientId":"1","pictureId":"1", "mark": "1"}'  http://localhost:8080/clients/vote
 	
 - to get all pictures
 	curl --header "Content-Type: application/json" --request GET  http://localhost:8080/pictures/all
 
 - to get picture by breed
 	curl --header "Content-Type: application/json" --request GET  http://localhost:8080/pictures/breed/retriever
 	
 - to get picture by id
 	curl --header "Content-Type: application/json" --request GET  http://localhost:8080/pictures/id/1	
 	
 