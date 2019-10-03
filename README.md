# MyBooks - A Case Study

## Problem Statement

Build a system to search for a book title, show results, add books to favourite list and recommend most liked/favourite books to user.

## Requirements

### The application needs to fetch book details from the following Open Library API.
https://openlibrary.org/developers/api

Refer the following URLs to explore more on the Books open library APIs.
https://openlibrary.org/dev/docs/api/search
https://openlibrary.org/dev/docs/api/books

### A frontend where the user can register/login to the application, search books by string, title or author, get books details, add book to favourite list and view recommended books.
### User can add a book to favourite list and should be able to view the favourite list.
### A recommendation service should be able to store all the unique favourite books from all the users and maintain counter for number of users added a particular book into favourite list.
### An option to view recommended books should be available on frontend. 

## Microservices/Modules
- UserService - should be able to manage user accounts.
- UI (User interface) -  should be able to
    - Search a book by string, title or author
    - View book details
    - Add a book to favourite list
    - should be able to view favourite books
    - should be able to view recommended books
- UI should be responsive which can run smoothly on various devices 
- FavouriteService - should be able to store and retrieve all the favourite books for a user
- BookRecommendationService - should be able to store all the unique favourite books from all the users and maintain counter for number of users added a particular book into favourite list.

## Tech Stack
- Spring Boot
- MySQL, MongoDB
- API Gateway
- Eureka Server
- Message Broker (RabbitMQ)
- Angular
- CI (Gitlab Runner)
- Docker, Docker Compose

## Flow of Modules

- Building frontend:
  1. Building responsive views:
    - Register/Login
    - Search for a book
    - Book list - populating from external API
    - Build a view to show favourite books
    - Build a view to show recommended books
  2. Using Services to populate these data in views
  3. Stitching these views using Routes and Guards
  4. Making the UI Responsive
  5. E2E and Unit tests
  6. Writing CI configuration file
  7. Dockerize the frontend

- Building the UserService
  1. Creating a server in Spring Boot to facilitate user registration and login using     JWT token and MySQL
  2. Writing swagger documentation
  3. Unit Testing
  4. Write CI Configuration
  5. Dockerize the application
  6. Write docker-compose file to build both frontend and backend application

- Create an API Gateway which can serve UI and API Request from same host

- Building the Favourite Service
  1. Building a server in Spring Boot to facilitate CRUD operation over favourite books    stored in MongoDB
  2. Writing Swagger Documentation
  3. Build a Producer for RabbitMQ which
    i. Produces events like what user added to favourite list
  4. Write Test Cases
  5. Write CI Configuration
  6. Dockerize the application
  7. Update the docker-compose

- Building the Book Recommendation Service
  1. Building a Consumer for RabbitMQ
    - i.  On a new event generated update the recommendations in the system. Store the        recommendation list in MongoDB.
    - ii. Maintain list of unique recommended books based on what user added into             favourite list and keep counter for number of users added a particular book         into favourite list
  2. Build an API to get Recommendations
  3. Writing Swagger Documentation
  4. Write Test Cases
  5. Write CI Configuration
  6. Dockerize the application
  7. Update the docker-compose
  8. Update the API Gateway

- Create Eureka server and make other services as client

- Demonstrate the entire application






## Solution Design:

User Interface:

Register and Login screen:
Register option allows the first time users to register for the application. ZuulService is an API gateway through which all requests are propagated to the respective microservices. The register functionality produces a queue with user details in the rabbitmq which is consumed by the userservice microservice.
Once sucessfully registered, the user can login to the application. The header has the following links available:
Browse (containing list of subjects)
My Books (containing Favourites and Recommended Books)
Logout. 
These links will only navigate when user successfully login to the application. 

#Browse 
The user can navigate to list of various subjects like Science, TextBooks, Sci-fi.These options are available in dropdown underbrowse. The auto selected option to display the subject is Fiction.

#Add The book in favourite List
User can click on the star icon in the card to add the book to the favourite list. Once clicked the 'favouriteservice' adds the book to the mongodb and also produces a queue with the book details of the user to be consumed by the 'bookrecommendationservice' microservice. The 'bookrecommendationservice' microservice stores this book detail and displayes under the Recommended section if it is liked by most of the user. (The book will be displayed in recommend section when atleast 3 users has liked the book.

#View favourite list and take corresponding actions
The user can navigate to the favourite list by clicking on My books -->Favourites mentioned in the header.Page contains the list of books liked by the user and can perform actions like- delete from favourites and update the comment for the book.

#View Recommended books
The user can navigate to the recommended books list by clicking My books -->Recommended link on the header. The 'bookrecommendationservice' service is invoked via zuulservice to fetch all the recommended books. Recommended books are shown when books are liked atleast by 3 users. 

#Service details:
1) userservice: Consumes the queue created by favouriteservice to store the login credentials of the user. Handles the login request of the application. These details are stored in the mysql database.

2) favouriteservice: Produces the queue for userservice and bookRecommendationService services. Also handles the request for adding book to favourite list, deleting from favourites list, fetching all books for the user to be displayed in the favourite list page.

3) BookRecommendationService: Consumes the book details of the user produced from user favouriteservice when the user adds the book to its favourites list.Fetches all the books which are recommended by more than 2 users.

Docker details:
The services and angular image is pushed in the docker hub public repository. The details are as:
1)MyBooksUI:-  aakbmir/mybooks:v1
2)userservice:- aakbmir/userservice:v1
3)favouriteservice service:- aakbmir/favouriteservice:v1
4)bookrecommendationservice:- aakbmir/recommendationservice:v1
5)zuulservice:- aakbmir/zuulservice:v1
6)eurekaservice:- aakbmir/eurekaservice:v1
docker-compose.yml file is created with the detail of all these images including the image of rabbitmq, mongo and mysql.

Running the application:
Ensure mysql and mongo should not be running before compiling the docker-compose up. Three commands to be run in the terminal for ubuntu os:

sudo service mongod stop
sudo service mysql stop
docker-compose up

After docker-compose up , to run the application in chrome : http://localhost:8080

To check the status on Eureka server whether all the services are up and running : http://localhost:9003
 
Port Details: - 
 All services will be running on the following ports
 
#rabbitmq:
	ports:
		- "5672:5672"
		- "15672:15672"

userservice:
	ports:
		- 8081:8081

favouriteservice:
	ports:
		- 8083:8083

recommendationservice:
	ports:
		- 8089:8089

eurekaServer:
	ports:
		- 9003:9003

zuulService:
	ports:
		- 8086:8086

mysql:
	ports:
		- 3306:3306

angular:
	ports:
		- 8080:8080

mongo:
	ports:
		- "27017:27017"






