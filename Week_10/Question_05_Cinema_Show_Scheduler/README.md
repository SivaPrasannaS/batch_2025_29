# Question 05: Cinema Show Scheduler

**Week 10: Validation & Exception Handling** · @Validated on path variables and query parameters, handling ConstraintViolationException

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
| `Description/Entity_Details.png` | Entity details table (image) |
| `Description/API_Endpoints.png` | API endpoint details table (image) |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `showControllerIsAnnotatedWithValidated` | Show Controller Is Annotated With Validated |
| 2 | `showControllerGetShowByIdParameterHasMinConstraint` | Show Controller Get Show By Id Parameter Has Min Constraint |
| 3 | `createShowWithValidDataReturnsCreated` | Create Show With Valid Data Returns Created |
| 4 | `createShowWithInvalidShowTimeReturnsBadRequest` | Create Show With Invalid Show Time Returns Bad Request |
| 5 | `getShowWithZeroIdReturnsBadRequest` | Get Show With Zero Id Returns Bad Request |
| 6 | `getShowByUnknownIdReturnsNotFound` | Get Show By Unknown Id Returns Not Found |
| 7 | `getShowsByScreenOutOfRangeReturnsBadRequest` | Get Shows By Screen Out Of Range Returns Bad Request |
| 8 | `getShowsByScreenReturnsOnlyMatchingShows` | Get Shows By Screen Returns Only Matching Shows |
| 9 | `getAvailableShowsReturnsShowsWithEnoughSeats` | Get Available Shows Returns Shows With Enough Seats |
| 10 | `getAvailableShowsWithZeroMinSeatsReturnsBadRequest` | Get Available Shows With Zero Min Seats Returns Bad Request |

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
