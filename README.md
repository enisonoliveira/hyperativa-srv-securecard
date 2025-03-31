

# HYPERATIVA-SRV-SECURECARD

## Overview
Welcome to the documentation for the **HYPERATIVA-SRV-SECURECARD** Web API. This API enables secure interactions with card entities and utilizes **Bearer token authentication** for secure access control. Data is temporarily stored in an **in-memory database**, making it ideal for development and testing environments.

## Key Features
- **In-Memory Database**: Lightweight and fast for testing purposes.
- **Bearer Token Authentication**: Secure token-based authentication to ensure authorized access.
- **RESTful Endpoints**: A collection of endpoints for card-related operations and user authentication.

---

## API Endpoints

### 1. **Generate Token**
**Endpoint:** `POST /api/token`  
**Purpose:** Generates a JWT token for authenticated access.

##defaul username:admin
##fataul password:123456

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "token": "string"
}
```

---

### 2. **Upload Card File**
**Endpoint:** `POST /api/cards/upload`  
**Purpose:** Uploads a file containing card data to be processed.

**Request Body:**
- `file`: Multipart file with card details in `.txt` format.

**Response:**  
Returns a status message indicating success or failure.

---

### 3. **Save Card**
**Endpoint:** `POST /api/cards/save`  
**Purpose:** Saves a new card entity into the database.

**Request Body:**
```json
{
  "cardNumber": "string",
  "holderName": "string",
  "expirationDate": "string",
  "cvv": "string"
}
```

**Response:**  
Returns a status message indicating success or failure.

---

### 4. **Search Card by Number**
**Endpoint:** `GET /api/cards/search/{number}`  
**Purpose:** Searches for a card entity by its number.

**Request Parameter:**
- `number` (Path Variable): The card number to search for.

**Response:**
```json
{
  "id": "integer"
}
```

---

## Security
This API uses **JWT Bearer Token Authentication**. All private endpoints require a valid JWT token to be included in the `Authorization` header:

**Authorization Header:**
```
Authorization: Bearer {token}
```

To obtain a token, use the `/api/token` endpoint with valid credentials.

---

## Local Development
### Prerequisites:
- Java 17+
- Spring Boot Framework
- Postman (optional for API testing)

### Running the Application
1. Clone the repository.
2. Start the server:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Access the API at `http://localhost:8080`.

---

## Postman Collection
Import the following Postman collection to test the API interactively.

```json
{
  "info": {
    "name": "HYPERATIVA-SRV-SECURECARD",
    "description": "Documentation for WEB-API HYPERATIVA-SRV-SECURECARD",
    "version": "V1",
    "_postman_id": "unique-id-for-your-collection",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Generate Token",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/api/token",
          "host": ["http://localhost:8080"],
          "path": ["api", "token"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"username\": \"string\",\n  \"password\": \"string\"\n}"
        }
      }
    },
    {
      "name": "Upload File",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/api/cards/upload",
          "host": ["http://localhost:8080"],
          "path": ["api", "cards", "upload"]
        },
        "body": {
          "mode": "formdata",
          "formdata": [
            {
              "key": "file",
              "type": "file",
              "src": ""
            }
          ]
        }
      }
    },
    {
      "name": "Save Card",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/api/cards/save",
          "host": ["http://localhost:8080"],
          "path": ["api", "cards", "save"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\n  \"cardNumber\": \"string\",\n  \"holderName\": \"string\",\n  \"expirationDate\": \"string\",\n  \"cvv\": \"string\"\n}"
        }
      }
    },
    {
      "name": "Search Card by Number",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/cards/search/{number}",
          "host": ["http://localhost:8080"],
          "path": ["api", "cards", "search", "{number}"],
          "variable": [
            {
              "key": "number",
              "value": "string"
            }
          ]
        }
      }
    }
  ]
}
```

Import the JSON above into Postman for quick API testing.

---
