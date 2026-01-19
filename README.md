# Employee Management System

A backend-focused Employee Management System built using Spring Boot as part of a Full Stack Intern Assessment.

---

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- H2 In-Memory Database
- Maven
- JUnit 5
- Mockito

---

## Features
- Create Employee
- Fetch Employee by ID
- Fetch all ACTIVE employees
- Update Employee
- Soft Delete Employee (status changed to INACTIVE)
- Bean Validation
- Global Exception Handling
- Unit and Controller Tests

---

## Project Structure
src/main/java/com/employee/employee_management
├── controller
├── service
├── repository
├── entity
├── enums
├── exception

Copy code
src/main/java/sectionb
├── TaxBracket.java
├── TaxCalculator.java

yaml
Copy code

---

## REST API Endpoints

| Method | Endpoint | Description |
|------|---------|-------------|
| POST | `/api/employees` | Create employee |
| GET | `/api/employees/{id}` | Get employee by ID |
| GET | `/api/employees/active` | Get all active employees |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Soft delete employee |

---

## Validation & Error Handling
- Bean validation using Jakarta Validation
- Centralized exception handling using `@ControllerAdvice`
- Proper HTTP status codes returned

---

## Database
- H2 In-memory database
- Auto schema generation enabled
- Unique constraint on employee email

H2 Console:
/h2-console

yaml
Copy code

---

## Section B — Payroll Tax Calculation (Pure Java)

Implemented as a separate module without Spring dependencies.

### Files
- `TaxBracket.java` — Represents tax slabs
- `TaxCalculator.java` — Calculates tax using progressive slabs
- `TaxCalculatorTest.java` — Unit tests validating logic

### Features
- Progressive tax calculation
- Edge case handling (zero/negative income)
- Fully unit tested

---

## Running the Application

### Build
mvn clean install

shell
Copy code

### Run
mvn spring-boot:run

shell
Copy code

### Run Tests
mvn test

yaml
Copy code

---

## Notes
- Frontend and deployment were intentionally excluded as the assessment focuses on backend implementation.
- Swagger/OpenAPI was skipped to avoid dependency conflicts with Spring Boot version.