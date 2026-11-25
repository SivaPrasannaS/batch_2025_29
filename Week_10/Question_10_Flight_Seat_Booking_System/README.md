# Question 10: Flight Seat Booking System

**Week 10: Validation & Exception Handling** · Full integration: body and parameter validation, 400 / 404 / 409 custom exceptions, malformed JSON and a fallback 500 handler

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding/springapp/` | Starter project given to students: `pom.xml`, `application.properties`, main class and TODO stubs for every required class |
| `scaffolding/junit/` | Assessment kit next to the student project: `junit.sh`, `Report/` (custom report) and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing question description as a Word document, with the entity and API images placed in sections 5 and 9 |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/Project_Structure.png` | Project structure tree (image) |
| `Description/Table_*.png` | Every other description table (validation rules, methods, exceptions, error response) as images |
| `Description/Entity_Details_Part1.png` | Entity details table (image) |
| `Description/Entity_Details_Part2.png` | Entity details table (image) |
| `Description/API_Endpoints.png` | API endpoint details table (image) |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `globalExceptionHandlerDeclaresFallbackHandlerForException` | Global Exception Handler Declares Fallback Handler For Exception |
| 2 | `addFlightReturnsCreatedWithAllSeatsAvailable` | Add Flight Returns Created With All Seats Available |
| 3 | `addFlightWithDuplicateFlightNumberReturnsConflict` | Add Flight With Duplicate Flight Number Returns Conflict |
| 4 | `addFlightWithSameOriginAndDestinationReturnsBadRequest` | Add Flight With Same Origin And Destination Returns Bad Request |
| 5 | `bookSeatsReducesAvailabilityAndCalculatesFare` | Book Seats Reduces Availability And Calculates Fare |
| 6 | `bookMoreSeatsThanAvailableReturnsBadRequest` | Book More Seats Than Available Returns Bad Request |
| 7 | `bookWithInvalidEmailAndZeroSeatsReportsBothFieldErrors` | Book With Invalid Email And Zero Seats Reports Both Field Errors |
| 8 | `bookOnUnknownFlightReturnsNotFound` | Book On Unknown Flight Returns Not Found |
| 9 | `getFlightWithZeroIdReturnsBadRequest` | Get Flight With Zero Id Returns Bad Request |
| 10 | `cancelBookingRestoresSeatsAndReturnsNoContent` | Cancel Booking Restores Seats And Returns No Content |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

In the assessment environment, `scaffolding/junit/junit.sh` copies `junit/test` into `springapp/src`, runs `mvn -q test` and prints the PASS/FAIL report through `Report/customReport.js`.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
- The testcase file uses only JDK and JUnit types (Spring Boot is reached through reflection and JSON through a built-in reader), so missing student classes or missing dependencies never stop it from compiling.
- Unexpected exceptions are reported as FAIL, never as errors. The application also has a 120-second startup limit. Checked with: no source code plus a pom that keeps only the test starter, a main class that cannot boot, an application that hangs on startup, and testcases that throw unexpected exceptions.
- The only case the testcases cannot cover is student code that does not compile. Maven compiles `src/main` before the tests, so the build stops before any testcase runs.
