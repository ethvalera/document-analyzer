# Document Analyzer

Document Analyzer is an application that tracks and analyzes text documents. The application is built using 
Spring Boot with PostgreSQL for data storage and Google Cloud's Vertex AI (Gemini) for AI-powered text analysis.

This project was developed using an API-first approach, where the API contract was designed and 
documented before implementation using OpenAPI 3.0 specifications.

## Features

- **Team Management**: Create and list teams
- **User Management**: Create users and associate them with teams
- **Document Analysis**:
    - Get document uploads
    - Count inactive users who registered before a given period but did not upload documents
    - Calculate word frequencies for specific documents
    - Find the longest word in a document and suggest synonyms using AI

Note: The application assumes that services already exist to upload and store documents, 
and another to retrieve the text content of a document.

## Architecture

The application follows a standard Spring Boot architecture:

- **Controller Layer**: REST API endpoints
- **Service Layer**: Business logic
- **Repository Layer**: Data access
- **AI**: Google Cloud Vertex AI (Gemini) for AI-powered text analysis

## Prerequisites

Before running the application, make sure you have:

1. **Docker** and **Docker Compose** installed

2. **Google Cloud SDK** installed and configured
    - Run `gcloud auth application-default login` to set up authentication

## Setup and Running

### 1. Clone the repository

```bash
git clone <repository-url>
cd docanalyzer
```

### 2. Set up Google Cloud credentials

```bash
gcloud auth application-default login
```

This will create credentials locally.

Change the exiting volumes parameter to the location of your credentials locally.
```bash
    volumes:
      - /Users/elisabeth/.config/gcloud/application_default_credentials.json:/app/credentials.json
```

### 3. Create a password file for the database

```bash
echo "password" > db_password.txt
```

### 4. Run with Docker Compose

```bash
docker-compose up -d
```

### 5. Access the application

The API will be available at http://localhost:8080/api/v1

## API Documentation

The application provides a RESTful API documented using OpenAPI 3.0.

### Endpoints Overview

- **Teams**
    - `GET /api/v1/teams` - List all teams
    - `POST /api/v1/teams` - Create a new team

- **Users**
    - `POST /api/v1/users` - Create a new user
    - `GET /api/v1/users/inactive` - Get count of inactive users

- **Documents**
    - `GET /api/v1/documents` - List all documents
    - `GET /api/v1/documents/{documentId}/word-frequencies` - Get word frequencies
    - `GET /api/v1/documents/{documentId}/longest-word-synonyms` - Get longest word and synonyms

### Viewing the API Documentation

You can view the full API documentation by using the OpenAPI YAML file: `docanalyzer-openapi.yaml`

## Development

### Building from source

```bash
mvn clean package
```

### Running tests

The project contains the following tests:
- Unit Tests
- Integration Tests
- Contract Tests

The application contains 65 tests.

```bash
mvn test
```
