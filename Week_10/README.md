# Week 10: Validation & Exception Handling: Assessment Set

Ten Spring Boot assessment questions for Week 10 of the course planner. All of them follow the same architecture (controller → service interface → service/impl → JpaRepository → entity, a request DTO with Bean Validation, and an `exception` package with custom exceptions, `ErrorResponse` and `GlobalExceptionHandler`), each with its own business scenario.

## Planner topics covered

1. Bean Validation: `spring-boot-starter-validation`; `@NotNull`, `@NotBlank`, `@Size`, `@Email`, `@Min`, `@Max`, `@Pattern`
2. Validating requests: `@Valid` on `@RequestBody`, `@Validated` on path/query params; handling `MethodArgumentNotValidException`
3. Global exception handling: custom exceptions (`ResourceNotFoundException`), `@RestControllerAdvice` and `@ExceptionHandler`
4. Standard error responses: consistent error JSON (`timestamp`, `status`, `message`, `path`) with field-level validation errors

## Questions

| # | Project | Focus | Base URL |
|---|---|---|---|
| 01 | [Campus Library Membership Portal](Question_01_Campus_Library_Membership_Portal/) | Bean Validation basics: @NotNull, @NotBlank, @Size with @Valid and field-level errors | /api/members |
| 02 | [Tech Fest Participant Registration](Question_02_Tech_Fest_Participant_Registration/) | Format validation with @Email and @Pattern | /api/participants |
| 03 | [Blood Donor Registry System](Question_03_Blood_Donor_Registry_System/) | Numeric range validation with @Min and @Max (including boundary values) | /api/donors |
| 04 | [Vehicle Service Center Tracker](Question_04_Vehicle_Service_Center_Tracker/) | Custom ResourceNotFoundException handled globally with @RestControllerAdvice and @ExceptionHandler | /api/service-tickets |
| 05 | [Cinema Show Scheduler](Question_05_Cinema_Show_Scheduler/) | @Validated on path variables and query parameters, handling ConstraintViolationException | /api/shows |
| 06 | [Hospital Doctor Directory](Question_06_Hospital_Doctor_Directory/) | Custom DuplicateResourceException mapped to 409 Conflict, together with validation and 404 handling | /api/doctors |
| 07 | [Job Portal Applicant Profiles](Question_07_Job_Portal_Applicant_Profiles/) | All seven constraint annotations on one DTO, reporting every field error at once, and handling malformed JSON | /api/applicants |
| 08 | [Restaurant Order Management](Question_08_Restaurant_Order_Management/) | Nested DTO validation with @Valid on a list of child objects | /api/orders |
| 09 | [Warehouse Inventory Stock Control](Question_09_Warehouse_Inventory_Stock_Control/) | Business-rule exception (InsufficientStockException) combined with validation and 404 handling | /api/products |
| 10 | [Flight Seat Booking System](Question_10_Flight_Seat_Booking_System/) | Full integration: body and parameter validation, 400 / 404 / 409 custom exceptions, malformed JSON and a fallback 500 handler | /api/flights and /api/bookings |

## Folder layout (same for every question)

```text
Question_NN_Project_Title/
├── scaffolding/
│   ├── springapp/             starter project for students
│   └── junit/                 junit.sh, Report/, test/java/.../SpringappApplicationTests.java
├── Solution/springapp/        reference solution (+ tests in src/test)
├── Description/               Question_Description.md, Entity_Details*.png, API_Endpoints.png
└── README.md
```

## Technical baseline (taken from the provided assessment scaffolding)

- Java 17, Spring Boot 3.3.5, Maven, MySQL database (`mysql-connector-j`; `application.properties` points to `jdbc:mysql://localhost:3306/<db>?createDatabaseIfNotExist=true` with user `root` / password `examly`), base package `com.examly.springapp`, root project `springapp`
- The testcases do not need MySQL: they start the application on an H2 in-memory database (H2 is in `pom.xml` with `test` scope only)
- Added dependency: `spring-boot-starter-validation` (required for this week)
- Testcases follow the existing kit's style: student classes are looked up by reflection and the app is started on a random port inside a try block, so a missing class gives a FAIL rather than a compilation error.
- The testcase files import only JDK and JUnit types. Spring Boot is started through reflection with a 120-second limit, and JSON is read by a small built-in helper. A JUnit extension turns any unexpected exception into a FAIL, so every testcase ends as PASS or FAIL. This matters because `customReport.js` counts a surefire "error" as neither passed nor failed.

## Verification summary

- 10 questions × 10 testcases = **100 testcases**
- All 100 pass against the reference solutions
- All 10 scaffoldings compile, and their testcases fail cleanly (0 / 100 pass, no compilation errors)
- Robustness runs for all 10 questions (no source code + stripped pom; main class that cannot boot) and spot checks (application that hangs on startup; injected unexpected exceptions): 0 surefire errors in every run, every testcase PASS or FAIL

## Note on the assessment kit

`Report/customReport.js` in the provided kit read surefire reports from `/home/coder/project/workspace/backend/target/surefire-reports`, while `junit.sh` runs Maven in `/home/coder/project/workspace/springapp`. The copies here point to `.../springapp/target/surefire-reports` so the PASS/FAIL report finds the results. Nothing else in the kit was changed.
