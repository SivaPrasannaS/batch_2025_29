# Week 11: Logging & AOP Basics: Assessment Set

Ten Spring Boot assessment questions for Week 11 of the course planner. All follow the Week 10 architecture (controller → service interface → service/impl → JpaRepository → entity, DTOs, and an `exception` package with custom exceptions, `ErrorResponse` and `GlobalExceptionHandler`). Questions 1–3 add SLF4J logging and log configuration; questions 4–10 add an `aspect` package.

## Planner topics covered

1. Logging fundamentals: why logging instead of `System.out`; log levels
2. SLF4J & Logback: `Logger` / `LoggerFactory`; parameterized messages
3. Log configuration: `logging.level.<package>` and `logging.file.name`
4. AOP concepts: aspect, advice, join point, pointcut; `spring-boot-starter-aop`
5. Advice types: `@Before`, `@After`, `@AfterReturning`, `@Around` with `execution()` pointcuts

## Questions

| # | Project | Focus | Base URL |
|---|---|---|---|
| 01 | [Hostel Room Allocation System](Question_01_Hostel_Room_Allocation_System/) | SLF4J Logger and LoggerFactory instead of System.out; INFO logging for normal operations | /api/rooms |
| 02 | [Student Bus Pass Renewal Portal](Question_02_Student_Bus_Pass_Renewal_Portal/) | Choosing log levels (DEBUG, INFO, WARN, ERROR) and parameterized log messages | /api/bus-passes |
| 03 | [College Canteen Order Service](Question_03_College_Canteen_Order_Service/) | Log configuration: logging.file.name and logging.level per package | /api/canteen-orders |
| 04 | [Sports Equipment Lending Desk](Question_04_Sports_Equipment_Lending_Desk/) | First aspect: @Aspect, @Component and @Before with an execution() pointcut | /api/equipment |
| 05 | [Exam Hall Ticket Generator](Question_05_Exam_Hall_Ticket_Generator/) | @AfterReturning (with the returned value) and @After advice | /api/hall-tickets |
| 06 | [Courier Parcel Tracking Service](Question_06_Courier_Parcel_Tracking_Service/) | @Around advice measuring execution time, with results exposed through a metrics endpoint | /api/parcels and /api/metrics |
| 07 | [Bank Locker Access Audit System](Question_07_Bank_Locker_Access_Audit_System/) | Audit-trail aspect: @AfterReturning saves an audit record after successful operations | /api/lockers and /api/audit-logs |
| 08 | [Concert Ticket Booking Service](Question_08_Concert_Ticket_Booking_Service/) | Controller-layer request logging with @Around (start, duration and failures) | /api/concerts |
| 09 | [Pet Clinic Appointment Manager](Question_09_Pet_Clinic_Appointment_Manager/) | Reusable named @Pointcut definitions shared by several advices | /api/appointments |
| 10 | [Food Delivery Order Tracker](Question_10_Food_Delivery_Order_Tracker/) | Full integration: SLF4J levels, log file configuration, all four advice types and a metrics endpoint | /api/orders and /api/metrics |

## Folder layout (same for every question)

```text
Question_NN_Project_Title/
├── scaffolding/
│   ├── springapp/             starter project for students
│   └── junit/                 junit.sh, Report/, test/java/.../SpringappApplicationTests.java
├── Solution/springapp/        reference solution (+ tests in src/test)
├── Description/               Question_Description.docx / .md and all table images
└── README.md
```

## Technical baseline

- Java 17, Spring Boot 3.3.5, Maven, MySQL database (`mysql-connector-j`; `application.properties` points to `jdbc:mysql://localhost:3306/<db>?createDatabaseIfNotExist=true` with user `root` / password `examly`), base package `com.examly.springapp`, root project `springapp`
- The testcases do not need MySQL: they start the application on an H2 in-memory database (H2 is in `pom.xml` with `test` scope only)
- Questions 4–10 add `spring-boot-starter-aop` to `pom.xml`; no other new dependency (SLF4J and Logback come with Spring Boot)
- Testcases use only JDK and JUnit types, start the app through reflection with a 120-second limit, mirror `System.out` to check log lines, read log files where configured, and turn every unexpected exception into a FAIL.

## Verification summary

- 10 questions × 10 testcases = **100 testcases**
- All 100 pass against the reference solutions
- All 10 scaffoldings compile, and their testcases fail cleanly (0 / 100 pass, no compilation errors)
