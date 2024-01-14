# Spring Boot Event API

## Overview

This project is a Spring Boot API for managing events. 
It provides endpoints to retrieve events based on multiple criteria. 
The API is built using Java 8, Spring Boot 2.7.18, Springdoc-openapi, and utilizes MySQL as the database. Additionally, Docker is used for containerization.

The API provides a flexible GET endpoint that allows users to retrieve events based on partial matching of various criteria, including event reference, descriptions, location code, teacher name, teacher email, and date range. 

## Features

- **Event Retrieval**: Retrieve events based on partial matching of event reference, descriptions, location code, teacher name, and teacher email. Filter events within a specified date range.

- **Swagger Documentation**: Utilizes Springdoc-openapi to generate Swagger documentation, making it easy for developers to explore and understand the API.

- **Containerized Deployment**: The application can be easily deployed using Docker, streamlining the setup process and ensuring consistent behavior across different environments.

- **Database Interaction**: The API interacts with a MySQL database, following a clean and structured schema for the `event` entity.

## Prerequisites

Before you start, make sure you have the following installed:

- Java 8
- Maven
- Docker
- MySQL

## Objectives: 
1. Build the project.
2. Create a base Docker image with Ubuntu and jdk8u202.
3. Create a custom Docker network. 
4. Run a MySQL container and attach it to the network. 
5. Create a Docker image of the Spring Boot app. 
6. Run a container of the Spring Boot app and attach it to the same network.


## Project Structure
```
.
├── src
│ ├── main
│ │ ├── java
│ │ │ └── co
│ │ │ └── uk
│ │ │ └── apg
│ │ │   └── config
│ │ │   └── controller
│ │ │   └── dto
│ │ │   └── entity
│ │ │   └── exception
│ │ │     └── handler
│ │ │   └── repository
│ │ │   └── service
│ │ │     └── impl
│ │ ├── resources
│ │ │ └── application.properties
│ │ │ └── init.sql
│ ├── test
│ │ └── java
│ │ └── co
│ │ └── uk
│ │ └── apg
│ │ └── ...
├── target
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── ...
```

## Setup and Deployment: 

1. Clone the repository:

    ```bash
    git clone https://github.com/r-ali-ashik/technical-exercise.git
    ```
2. Build and create the package
    ```bash
    mvn clean package
    ```
3. Build the base image: 
    ```dockerfile
   # Use Ubuntu as the base image
   FROM ubuntu:latest

   # Update and install necessary packages
   RUN apt-get update && apt-get install -y tar \
   && rm -rf /var/lib/apt/lists/*

   # Copy JDK files to the container
   COPY jdk-8u202-linux-x64.tar.gz /tmp/

   # Extract JDK
   RUN tar xvzf /tmp/jdk-8u202-linux-x64.tar.gz -C /usr/local \
   && mv /usr/local/jdk1.8.0_202 /usr/local/jdk \
   && rm /tmp/jdk-8u202-linux-x64.tar.gz

   # Set environment variables
   ENV JAVA_HOME /usr/local/jdk
   ENV PATH $PATH:$JAVA_HOME/bin

   #Create direcotry for the target jdk
   RUN mkdir -p /app/target
   # Set the working directory
   WORKDIR /app

   # Expose the port on which the Spring Boot application will run
   EXPOSE 8080
   ```
NOTE: This Docker image file requires a local JDK in the context root of the `Dockerfile`. This Docker image can be found on Docker Hub under `raliashik/ubuntu-jdk8`.

4. Run a containerized `MySQL`
```dockerfile
version: '3.1'
services:
  mysqldb:
    container_name: MySQL
    image: mysql:8.0.23
    command: --default-authentication-plugin=mysql_native_password
    restart: always
    environment:
      MYSQL_USER: testUser
      MYSQL_PASSWORD: testPassword
      MYSQL_ROOT_PASSWORD: admin
      TZ: UTC
      MYSQL_DATABASE: test_db
      MYSQL_HOST: 127.0.0.1
    ports:
      - 3306:3306  
    networks:
      - common-bridge-network
networks:
  common-bridge-network:
    external: true
```
NOTE: This container runs on a external docker network `common-bridge-network`

5. Build the docker image out of the spring boot app: 
```dockerfile
# Use Ubuntu as the base image
FROM raliashik/ubuntu-jdk8:1.0

COPY target/technical-exercise.jar /app/target/

# Expose the port on which the Spring Boot application will run
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "target/technical-exercise.jar"]
```
NOTE: It uses the same base image built on the Step 3. This Docker image can be found on Docker Hub under `raliashik/technical-exercise`.

6. Deploy the app
```dockerfile
version: '3.7'
services:
  springboot-app:
    build:
      context: .
    container_name: spring-boot-app
    image: raliashik/technical-exercise:1.0
    ports:
      - "8080:8080"
    networks:
      - common-bridge-network
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysqldb:3306/test_db
      - SPRING_DATASOURCE_USERNAME=testUser
      - SPRING_DATASOURCE_PASSWORD=testPassword
networks:
  common-bridge-network:
    external: true
```
NOTE: This container runs on the same external docker bridge network that MySQL container runs

## Challenge:
Indecision was the biggest challenge; 
I spent some time decising which approach to follow for building and deploying the Docker image. 
Initially, I considered using any base image from the public registry. 
However, for better control, I ultimately decided to build my own Docker image.

The goal was to create a single Docker image and place the Dockerfile in the repository. 
and to provide my own JDK during the image-building process. 
In that case I had to include the local JDK file in the repository since the Dockerfile can only read files from its context root.
However, it later became evident that putting the JDK directly into the repository would have been a suboptimal idea.

## Features I would add: 
* As the application is running in a container, we don't have easy access to the logs. So, there isn't an easy way to monitor the logs. I would integrate a distributed monitoring and analytics system with the app.
* Currently, I am building and packaging the app manually. I would integrate GitHub Actions as a CI/CD pipeline to automate the building and deployment process. The pipeline would build and package the app, create a Docker image, upload the image into the registry, and initiate a deployment script that pulls the image from the registry and deploys the container.
* Considering the infrequent changes in the nature of the data, I would install a caching machanism.
* I would add pagination support to the API
* I would aim for 100% test coverage.

## Conclusion: 
Find the application swagger in the following URL 
`http://localhost:8080/swagger-ui/index.html`