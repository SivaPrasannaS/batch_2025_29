# Question 09: Pet Clinic Appointment Manager

**Week 11: Logging & AOP Basics** · Reusable named @Pointcut definitions shared by several advices

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
| 1 | `aspectDeclaresNamedControllerAndServicePointcuts` | Aspect Declares Named Controller And Service Pointcuts |
| 2 | `advicesReferenceNamedPointcuts` | Advices Reference Named Pointcuts |
| 3 | `bookAppointmentReturnsCreatedAsScheduled` | Book Appointment Returns Created As Scheduled |
| 4 | `controllerCallIsLogged` | Controller Call Is Logged |
| 5 | `serviceCallIsLogged` | Service Call Is Logged |
| 6 | `controllerLogComesBeforeServiceLog` | Controller Log Comes Before Service Log |
| 7 | `successfulServiceCallIsLogged` | Successful Service Call Is Logged |
| 8 | `cancelAppointmentSetsStatusCancelled` | Cancel Appointment Sets Status Cancelled |
| 9 | `failedCancelIsCalledButNotLoggedAsSuccess` | Failed Cancel Is Called But Not Logged As Success |
| 10 | `getAppointmentsByVetReturnsOnlyMatching` | Get Appointments By Vet Returns Only Matching |

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
