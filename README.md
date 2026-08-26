# 📌 Job Application Tracker API

A Spring Boot RESTful API for managing the complete lifecycle of job applications. The project focuses on domain validation, finite-state-machine constraints, automated database auditing, server-side pagination, structured global error handling, and automated unit testing.

---

## 📸 Screenshots & API Walkthrough

### 1. Interactive OpenAPI / Swagger Documentation

Overview of all REST endpoints exposed by the service.

![Swagger API Overview](docs/images/01-swagger-overview.png)

### 2. Create Job Application (`POST /api/jobs`)

Successful creation of a job application returning a `201 Created` status with auto-generated timestamps.

![Create Application](docs/images/02-create-job.png)

### 3. Fetch All Applications (`GET /api/jobs`)

Retrieving persisted application records from the database.

![Get Applications](docs/images/03-get-all-jobs.png)

### 4. Update Job Application & State Machine (`PUT /api/jobs/{id}`)

Updating application details and transitioning state while preserving the original `createdAt` timestamp.

![Update Application](docs/images/04-update-job.png)

### 5. Server-Side Pagination & Dynamic Filtering

Efficiently retrieving data in bounded pages using Spring Data JPA `Pageable` (`page`, `size`, `sortBy`) and custom filters.

![Pagination and Search](docs/images/05-pagination-filter.png)

### 6. Centralized Error Handling & Validation

Predictable, structured JSON error response when querying a non-existent resource (`404 Not Found`).

![Error Handling](docs/images/06-error-handling.png)

### 7. Aggregate Statistics (`GET /api/jobs/stats`)

Real-time breakdown and aggregate counts of applications grouped by their current lifecycle status.

![Job Statistics](docs/images/07-job-stats.png)

---

## 🚀 Key Features & Architectural Highlights

* **Clean Layered Architecture:** Separates HTTP handling, business logic, persistence, and API data models across Controller, Service, Repository, and DTO layers.
* **Finite-State Machine Constraints:** Enforces valid lifecycle transitions (`APPLIED` → `INTERVIEW_SCHEDULED` → `OFFERED` / `REJECTED`) and blocks illegal modifications on terminal states.
* **Automated Database Auditing:** Automatically tracks `createdAt` and `updatedAt` metadata through JPA/Hibernate auditing support.
* **Server-Side Pagination:** Uses Spring Data JPA `Pageable` to retrieve data in bounded pages instead of loading the complete result set into application memory.
* **Centralized Exception Handling:** Uses `@RestControllerAdvice` to map domain-specific and validation exceptions to consistent error responses.
* **Service-Layer Unit Testing:** Tests business logic using JUnit 5 and Mockito while isolating the Service layer from the database through repository mocking.
* **Interactive API Documentation:** Provides an OpenAPI 3.0 contract rendered through Swagger UI.

---

## 🧠 Engineering Decisions

### Why Layered Architecture?

The application separates HTTP handling, business rules, and persistence responsibilities.

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database

The Controller handles HTTP concerns, the Service owns business rules, and the Repository handles persistence. This separation reduces coupling and allows business logic to be tested independently of HTTP and database infrastructure.
Why DTOs Instead of Exposing JPA Entities?
The API uses DTOs at the application boundary instead of directly exposing persistence entities.
This keeps the external API contract separate from the database model, allowing the persistence structure and API representation to evolve independently.
Why Validate Job Status Transitions?
A job application has a defined lifecycle rather than allowing arbitrary status changes.
APPLIED
   ↓
INTERVIEW_SCHEDULED
   ├──→ OFFERED
   └──→ REJECTED

Terminal states cannot be changed back to earlier states.
These transition rules are enforced in the Service layer because they represent business rules rather than HTTP or persistence concerns.
Why Server-Side Pagination?
Returning every application in a single response does not scale with dataset size.
Using Pageable allows the application to request only the required page of records from the database. This keeps response sizes bounded and avoids loading the entire dataset into application memory.
Why Centralized Exception Handling?
Exception handling is centralized using @RestControllerAdvice instead of repeating error-handling logic across individual controllers.
This provides a consistent error response structure and keeps controller methods focused on request handling.
Why Automated Database Auditing?
createdAt and updatedAt represent persistence metadata rather than client-provided business data.
They are therefore maintained automatically instead of requiring API clients to provide or modify these values.
Why Unit Test the Service Layer?
The Service layer contains important business rules such as job-status transition validation.
Mockito is used to isolate the Service from the Repository, allowing these rules to be tested without requiring a running database.

🔄 Request & Persistence Flow
A typical request follows this path:
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
Spring Data JPA
     ↓
Hibernate
     ↓
JDBC
     ↓
MySQL
Each layer has a distinct responsibility:
Controller: Handles the HTTP/API boundary.
Service: Applies application and business rules.
Repository: Provides the persistence abstraction.
Spring Data JPA: Provides repository infrastructure and query abstraction.
Hibernate: Implements the JPA persistence and ORM behavior.
JDBC: Provides the Java database connectivity layer.
MySQL: Persists the relational data.
🛠️ Tech Stack
Language: Java 17+
Framework: Spring Boot 3
Persistence: Spring Data JPA, Hibernate, MySQL
Testing: JUnit 5, Mockito
API Documentation: SpringDoc OpenAPI 3 (Swagger UI)
Build Tool: Maven

🔌 API Endpoints Reference
HTTP Method
Endpoint
Description
Request Body / Params
POST
/api/jobs
Create a new application
JobApplicationRequestDTO
GET
/api/jobs
Get paginated application records
page, size, sortBy
GET
/api/jobs/{id}
Get application details by ID
Path variable id
PUT
/api/jobs/{id}
Update application details & state
JobApplicationRequestDTO
DELETE
/api/jobs/{id}
Remove an application by ID
Path variable id
GET
/api/jobs/stats
Retrieve aggregate metrics by status
None
🧪 Running Unit Tests
Execute the automated unit test suite locally:
./mvnw test