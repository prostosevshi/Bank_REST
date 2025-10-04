# Bank Card Management System

## Technologies
- Java 17+
- Spring Boot (Web, Security, Data JPA)
- PostgreSQL
- Liquibase
- JWT
- Swagger / OpenAPI
- Docker / Docker Compose
- JUnit / Mockito

## Getting Started

### 1. Clone the repository
git clone https://github.com/your-username/bank-rest.git

### 2. Start the database
docker-compose up -d

### 3. Run the application
./mvnw spring-boot:run

### After the application starts, you can open Swagger UI:
http://localhost:8080/swagger-ui/index.html

You can test all endpoints there without Postman.

- /api/auth/login – user login
- /api/auth/register – user registration
- /api/cards – manage bank cards
- /api/transfers – money transfers between cards

### Admin Access
Some endpoints require ADMIN role.
To log in as admin use:

- username: admin
- password: admin

Once logged in, copy the received JWT token and use it in the Authorize button in Swagger UI.
