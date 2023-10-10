# Postal Service

## Introduction

The Distributed Postal Service is a prototype application designed to manage the sending of letters and packages through a distributed system

## System Architecture

The system is built as a distributed system, allowing for scalability and expansion in the future. It consists of the following main components:

* JavaFX Frontend: Provides a user interface for entering and displaying data.
* Spring Boot REST Service: Manages data storage in a PostgreSQL database, publishes queue messages to ActiveMQ, and handles API requests.
* Java Queue Workers: Comprises two services, the **Letter Service** and the **Package Service**, which subscribe to the ActiveMQ queue, process data from the database, and update status based on certain requirements.

## Components

### JavaFX Frontend

* Input Data: Allows users to enter names and country/weight for sending letters or packages. show Alert pop-up at empty or wrong input
* Actions: Provides a "Send" button for initiating the sending process via html.client and a "Refresh" button to check the status.
* Displays Data: Receives and displays data received from the Spring Boot service.

### Spring Boot REST Service

* API Endpoints:
    * POST /api/letters/letter: Initiates the sending of letters in JSON format.
    * POST /api/packages/package: Initiates the sending of packages in JSON format.
    * GET /api/status: Retrieves the status of letters and packages.

* Functionality:
    * Saves data to the PostgreSQL database using ORM - Jakarta persistance.
    * Publishes messages to the ActiveMQ queue using Producer class.
    * Retrieves data from the database using ORM - Jakarta persistance.
    * Sends/recieves data to the JavaFX frontend via API.

### Queue Workers

* Letter Service:
    * Subscribes to the "letter" queue listen, Listen with JMSListener.
    * Retrieves letter data from the database using ORM - Jakarta persistance.
    * Checks if requirements to send are met (e.g., valid countries AT, DE and CH).
    * Updates status in the database.

* Package Service:
    * Subscribes to the "package" queue listen, Listen with JMSListener.
    * Retrieves package data from the database using ORM - Jakarta persistance.
    * Checks if requirements to send are met (e.g., weight < 25kg).
    * Updates status in the database.


## Technologies Used

ActiveMQ Queue: Used for message queuing and communication between components.

PostgreSQL Database: Stores data on tables letter and package on databse postdb.

### Table Structure

* Letter Table:

| Column  |  Type         | Constraints     |
|---------|---------------|-----------------|
| id      | serial        | PRIMARY KEY     |
| name    | VARCHAR(255)  | NOT NULL        |
| country | VARCHAR(255)  | NOT NULL        |
| status  | VARCHAR(255)  | NOT NULL        |

* Package Table:

| Column  |  Type            | Constraints         |
|---------|------------------|---------------------|
| id      | serial           | PRIMARY KEY         |
| name    | VARCHAR(255)     | NOT NULL            |
| weight  | DOUBLE PRECISION | NOT NULL            |
| status  | VARCHAR(255)     | NOT NULL            |


## Setup and Configuration

* Docker and Docker-Compose should be installed on PC
* OpenJDK 17.0 should be installed for JavaFX app
* start all activemq, postgresql, sprinbootapi, letterservice and packageservice by executing `./start_simple.sh`  (or in windows with `start_simple.bat` file - not tested)
 


