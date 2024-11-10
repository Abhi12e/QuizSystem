#  Online Quiz System


## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Testing](#testing)
- [Database Setup](#database-setup)


## Introduction

Welcome to the Online Quiz System . This application provides the backend services for a quiz application. It allows you to manage quizzes, questions, and user responses.

## Features

1. **User Management:** It helps to register and login to take the quizzes.
2. **Question Management:** Add, edit, and remove questions within quizzes.
3. **User Responses:** Capture and evaluate user responses to quiz questions.
4. **Performance Tracking:**  Provide detailed reports on user performance.


## Technologies Used

- **Java 11**
- **Spring Boot:** The primary framework for building the backend.
- **MySQL:** The database used to store quiz ,  question  and the user data.
- **Spring Data JPA:** Java ORM for mapping objects to databases with features.
- **JUnit:** Unit and integration testing framework


## Testing
- You can test the application using Postman to make requests to the different endpoints:

- User Registration:   Use the /register endpoint with a POST request to register a new user.
- User Login:    Test the /login endpoint with a POST request to authenticate user credentials.
- Submit Quiz Answers:     Use the /quiz/submit endpoint with a POST request to submit quiz answers.

  
## Database Setup

1. Install MySQL and create a new database.
2. Update the `application.properties` file with your database configuration.

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect


