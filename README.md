📌 Job Application Tracker API

A Spring Boot RESTful API for managing the complete lifecycle of job applications. The project focuses on domain validation, finite-state-machine constraints, automated database auditing, server-side pagination, structured global error handling, and automated unit testing.


---

📸 Screenshots & API Walkthrough

1. Interactive OpenAPI / Swagger Documentation

Overview of all REST endpoints exposed by the service.


2. Create Job Application (POST /api/jobs)

Successful creation of a job application returning a 201 Created status with auto-generated timestamps.


3. Fetch All Applications (GET /api/jobs)

Retrieving persisted application records across the database.


4. Update Job Application & State Machine (PUT /api/jobs/{id})

Updating application details and transitioning state while preserving the original createdAt timestamp.


5. Server-Side Pagination & Dynamic Filtering

Efficient database querying using Spring Data JPA Pageable (page, size, sortBy) and custom filters.


6. Centralized Error Handling & Validation

Predictable, structured JSON error response when querying a non-existent resource (404 Not Found).


7. Aggregate Statistics (GET /api/jobs/stats)

Real-time breakdown and aggregate counts of applications grouped by their current lifecycle status.



---

🚀 Key Features & Architectural Highlights

Clean Layered Architecture: Strict separation of concerns across Controller, Service, Repository, and DTO layers.

Finite-State Machine Constraints: Enforces valid lifecycle transitions (APPLIED → INTERVIEW_SCHEDULED → OFFERED / REJECTED) and blocks illegal modifications on terminal states.

Automated Database Auditing: Automatic tracking of createdAt and updatedAt metadata via Hibernate annotations.

Optimized SQL Execution: Server-side pagination (LIMIT / OFFSET) to prevent memory bottlenecks on large datasets.

Centralized Exception Handling: Global @RestControllerAdvice mapping domain-specific and validation exceptions to uniform error schemas.

Automated AAA Unit Testing: Service layer completely verified using JUnit 5 and Mockito with isolated repository mocking.

Interactive Documentation: Live OpenAPI 3.0 contract rendered dynamically via Swagger UI.



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