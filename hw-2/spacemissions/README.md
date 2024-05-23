# SpaceMissions Project

## Overview

The SpaceMissions project is a Spring Boot application designed to manage and expose data related to space missions and spaceships. This project demonstrates the use of REST APIs to perform CRUD operations and more complex data handling tasks. The data is stored in a PostgreSQL database, and the application ensures proper validation, error handling, and data integrity.

## Entities

### Entity 1: Mission
- `id`: Big integer, primary key, auto-generated
- `planet_name`: String, name of the planet
- `mission_year`: Integer, year of the mission

### Entity 2: Spaceship
- `id`: Big integer, primary key, auto-generated
- `spaceship_name`: String, name of the spaceship
- `destination_planet`: String, destination planet
- `capacity`: Integer, capacity of the spaceship
- `mission_id`: Big integer, foreign key, references `Mission(id)`

## Endpoints

### Mission Endpoints

#### Create a new Mission

- **POST /api/missions**
  - Request Body: JSON representing a new mission
  - Validation: Required fields must be provided and properly formatted

#### Get Mission by ID
- **GET /api/missions/{id}**
  - Returns detailed data of the specified mission, including the related spaceship data
 
#### List All Missions
- **GET /api/missions**
  - Returns: A list of all missions in the database.
  - _Note: This endpoint is created for convenience to quickly retrieve all mission data without any filters or pagination._

#### Update Mission by ID
- **PUT /api/missions/{id}**
  - Request Body: JSON with updated mission data
  - Validation: Ensure data is properly formatted

#### Delete Mission by ID
- **DELETE /api/missions/{id}**

#### List Missions with Pagination
- **POST /api/missions/_list**
  - Request Body: JSON specifying filters and pagination (e.g., `entity2Id`, `page`, `size`)
  - Returns: List of missions matching the criteria, including pagination info

#### Generate Report for Missions
- **POST /api/missions/_report**
  - Request Body: JSON specifying filters
  - Returns: A downloadable file CSV containing all records matching the criteria

#### Upload Missions from File
- **POST /api/missions/upload**
  - Request Body: JSON file with mission data
  - Returns: JSON summarizing the number of successfully imported records and errors

### Spaceship Endpoints

#### List All Spaceships
- **GET /api/spaceship**

#### Create a new Spaceship
- **POST /api/spaceship**
  - Request Body: JSON representing a new spaceship
  - Validation: Ensure unique spaceship names

#### Update Spaceship by ID
- **PUT /api/spaceship/{id}**
  - Request Body: JSON with updated spaceship data
  - Validation: Ensure unique spaceship names

#### Delete Spaceship by ID
- **DELETE /api/spaceship/{id}**

## Database

The database schema and initial data are managed using Liquibase. The Liquibase script creates the necessary tables and populates them with initial data for entities.

## Integration Tests

Each endpoint is covered by integration tests to ensure they work as expected. The tests validate the functionality, error handling, and data integrity of the endpoints.

## Getting Started

### Prerequisites
- Java 17
- PostgreSQL
- Maven

### Setup
1. Clone the repository
   ```bash
   git clone https://github.com/DarinaViktorova/ProfITSoft-Internship

2. Configure the PostgreSQL database

- Update the application.properties file with your PostgreSQL credentials

3. Run the application 
4. Access the application at http://localhost:8080
