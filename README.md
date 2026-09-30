# Student Management REST API

A RESTful Student Management API built with Java, Spring Boot, Spring Data JPA, and PostgreSQL.

The application provides CRUD operations for managing student records and includes input validation, exception handling, database persistence, and automated testing.

## Technologies

- Java
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Hibernate
- Jakarta Bean Validation
- Maven
- JUnit 5
- Mockito
- MockMvc
- Git & GitHub

## Features

- Create a student
- View all students
- View a student by ID
- Update a student
- Delete a student
- PostgreSQL database persistence
- GPA validation from 0.0 to 4.0
- Required name and major validation
- Global validation error handling
- HTTP status handling
- Service layer architecture
- Repository layer using Spring Data JPA
- Automated service and controller tests

## Project Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL Database
```

The application separates responsibilities into:

- `StudentController` — handles HTTP requests and responses
- `StudentService` — contains application logic
- `StudentRepository` — communicates with PostgreSQL
- `Student` — JPA entity representing a student
- `GlobalExceptionHandler` — handles validation errors

## REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/students` | Get all students |
| GET | `/students/{id}` | Get a student by ID |
| POST | `/students` | Create a student |
| PUT | `/students/{id}` | Update a student |
| DELETE | `/students/{id}` | Delete a student |

## Example Student

```json
{
  "id": 1,
  "name": "Darshdeep Singh",
  "major": "Computer Science",
  "gpa": 3.76
}
```

## Validation

Student data is validated before it is saved.

- Name cannot be blank
- Major cannot be blank
- GPA must be between `0.0` and `4.0`

Example validation response:

```json
{
  "gpa": "GPA must not be greater than 4.0"
}
```

## HTTP Status Codes

The API uses appropriate HTTP status codes, including:

- `200 OK` — successful GET or PUT
- `201 Created` — student successfully created
- `204 No Content` — student successfully deleted
- `400 Bad Request` — validation failure
- `404 Not Found` — student does not exist

## Database Configuration

The application uses PostgreSQL.

Create a PostgreSQL database named:

```text
student_api
```

The database password is supplied through an environment variable rather than stored directly in the source code.

Set:

```text
DB_PASSWORD
```

The application configuration uses:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_api
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

Do not commit database passwords or other credentials to GitHub.

## Running the Application

Clone the repository:

```bash
git clone git@github.com:Dsingh35718/student-management-api.git
cd student-management-api
```

Set the PostgreSQL password in your environment:

```bash
export DB_PASSWORD="your_postgresql_password"
```

Run the application with Maven:

```bash
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

For example:

```bash
curl http://localhost:8080/students
```

## Testing

The project contains automated tests for the application, service layer, and REST controller.

The tests cover functionality such as:

- Retrieving students
- Creating students
- Updating students
- Deleting students
- `200`, `201`, `204`, `400`, and `404` responses
- Input validation

Run the tests with:

```bash
./mvnw test
```

## Future Improvements

Possible future enhancements include:

- Docker containerization
- Cloud deployment
- Pagination and sorting
- Search functionality
- DTOs
- Authentication and authorization
- CI/CD

## Author

Darshdeep Singh