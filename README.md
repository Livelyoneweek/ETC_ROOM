# ETC Spring Boot Project

This project is a Spring Boot application that demonstrates various features and best practices.  
It serves as a collection of examples for building modern Java applications.

## Tech Stack

* **Framework:** Spring Boot 3.2.5
* **Language:** Java 17
* **Build Tool:** Gradle
* **Database:**
    * Spring Data JPA
    * QueryDSL for type-safe queries
    * H2 (In-memory database)
* **Libraries:**
    * Lombok
    * ZXing (for QR Code generation)
    * P6Spy (for SQL query logging)
    * ArchUnit (for architecture testing)

## Key Features

* **RESTful APIs:** Demonstrates creating REST controllers with Spring MVC.
* **JPA & QueryDSL:** Shows how to interact with a database using Spring Data JPA and build complex, type-safe queries
  with QueryDSL.
* **Profile Management:** Example of managing application configuration using Spring Profiles (`dev`, `prd`, `local`,
  etc.).
* **QR Code Generation:** Includes services for creating QR codes.
* **Architectural Testing:** Uses ArchUnit to enforce architectural rules and maintain code quality.
* **Modern Java:** Utilizes Java 17 features.

## How to Run

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd etc
   ```

2. **Build the project:**
   ```bash
   ./gradlew build
   ```

3. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```
   The application will start on `http://localhost:8080`.

## API Endpoints

* `/company`: Manages company data.
    * `GET /company?id={companyId}`: Find company by ID.
    * `GET /company/id`: Get a list of all company IDs.
    * `GET /company/querydsl`: Get a list of all company IDs using QueryDSL.
* `/api`: General utility endpoints.
    * `GET /api/temp`: Displays the currently active Spring profiles.

*(More endpoints related to QR codes, logging, etc. are available.)*