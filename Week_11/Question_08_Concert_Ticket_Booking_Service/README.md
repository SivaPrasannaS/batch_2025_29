# Question 08: Concert Ticket Booking Service

**Week 11: Logging & AOP Basics** · Controller-layer request logging with @Around (start, duration and failures)

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding/springapp/` | Starter project given to students: `pom.xml`, `application.properties`, main class and TODO stubs for every required class |
| `scaffolding/junit/` | Assessment kit next to the student project: `junit.sh`, `Report/` (custom report) and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing question description as a Word document with every table as an image |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/*.png` | Project structure, entity details, API endpoints and every other description table as images |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `aspectHasAroundAdviceOnControllerPackage` | Aspect Has Around Advice On Controller Package |
| 2 | `aroundAdviceTakesProceedingJoinPointAndReturnsObject` | Around Advice Takes Proceeding Join Point And Returns Object |
| 3 | `createConcertReturnsCreatedWithAllSeatsAvailable` | Create Concert Returns Created With All Seats Available |
| 4 | `requestStartIsLogged` | Request Start Is Logged |
| 5 | `requestCompletionIsLoggedWithDuration` | Request Completion Is Logged With Duration |
| 6 | `bookSeatsReducesAvailableSeats` | Book Seats Reduces Available Seats |
| 7 | `failedRequestIsLoggedAsWarning` | Failed Request Is Logged As Warning |
| 8 | `failedRequestStillReachesExceptionHandler` | Failed Request Still Reaches Exception Handler |
| 9 | `serviceMethodsAreNotLoggedAsRequests` | Service Methods Are Not Logged As Requests |
| 10 | `unknownConcertIsLoggedAsFailedRequest` | Unknown Concert Is Logged As Failed Request |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

In the assessment environment, `scaffolding/junit/junit.sh` copies `junit/test` into `springapp/src`, runs `mvn -q test` and prints the PASS/FAIL report through `Report/customReport.js`.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
- Log checks read the console output (captured from `System.out`, where Logback writes) and, where configured, the log file under `logs/`.
- The testcase file uses only JDK and JUnit types and reports every unexpected exception as FAIL, so each testcase ends as PASS or FAIL.
