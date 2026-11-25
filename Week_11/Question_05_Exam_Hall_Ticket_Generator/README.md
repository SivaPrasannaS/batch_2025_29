# Question 05: Exam Hall Ticket Generator

**Week 11: Logging & AOP Basics** · @AfterReturning (with the returned value) and @After advice

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
| 1 | `loggingAspectHasAfterReturningAdviceWithReturningParameter` | Logging Aspect Has After Returning Advice With Returning Parameter |
| 2 | `loggingAspectHasAfterAdvice` | Logging Aspect Has After Advice |
| 3 | `hallTicketToStringUsesRequiredFormat` | Hall Ticket To String Uses Required Format |
| 4 | `generateTicketReturnsCreatedWithTicketNumber` | Generate Ticket Returns Created With Ticket Number |
| 5 | `afterReturningLogsReturnedTicket` | After Returning Logs Returned Ticket |
| 6 | `afterAdviceLogsCompletion` | After Advice Logs Completion |
| 7 | `countTicketsLogsReturnedCount` | Count Tickets Logs Returned Count |
| 8 | `duplicateRollNumberReturnsConflict` | Duplicate Roll Number Returns Conflict |
| 9 | `failedCallLogsCompletionButNotReturnValue` | Failed Call Logs Completion But Not Return Value |
| 10 | `unknownTicketReturnsNotFoundAndLogsCompletion` | Unknown Ticket Returns Not Found And Logs Completion |

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
