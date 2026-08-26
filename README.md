

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

---

🛠️ Tech Stack

Language: Java 17+

Framework: Spring Boot 3

Persistence: Spring Data JPA, Hibernate, MySQL

Testing: JUnit 5, Mockito

API Documentation: SpringDoc OpenAPI 3 (Swagger UI)

Build Tool: Maven



---

🔌 API Endpoints Reference

HTTP Method	Endpoint	Description	Request Body / Params

POST	/api/jobs	Create a new application	JobApplicationRequestDTO
GET	/api/jobs	Get paginated application records	page, size, sortBy
GET	/api/jobs/{id}	Get application details by ID	Path variable id
PUT	/api/jobs/{id}	Update application details & state	JobApplicationRequestDTO
DELETE	/api/jobs/{id}	Remove an application by ID	Path variable id
GET	/api/jobs/stats	Retrieve aggregate metrics by status	None



---

🧪 Running Unit Tests

Execute the automated unit test suite locally:

./mvnw test