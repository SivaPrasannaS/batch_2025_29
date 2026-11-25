# Question 10: Food Delivery Order Tracker

**Week 11: Logging & AOP Basics** · Full integration: SLF4J levels, log file configuration, all four advice types and a metrics endpoint

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
| 1 | `applicationPropertiesConfigureLogFileAndDebugLevel` | Application Properties Configure Log File And Debug Level |
| 2 | `aspectDeclaresAllFourAdviceTypes` | Aspect Declares All Four Advice Types |
| 3 | `placeOrderReturnsCreatedAndLogsInfo` | Place Order Returns Created And Logs Info |
| 4 | `placeOrderIsWrittenToLogFile` | Place Order Is Written To Log File |
| 5 | `adviceRunsInTheExpectedOrderOnSuccess` | Advice Runs In The Expected Order On Success |
| 6 | `aroundAdviceRecordsMetrics` | Around Advice Records Metrics |
| 7 | `failedCallLogsFinishedButNotCompleted` | Failed Call Logs Finished But Not Completed |
| 8 | `validStatusTransitionUpdatesOrder` | Valid Status Transition Updates Order |
| 9 | `invalidStatusTransitionReturnsBadRequest` | Invalid Status Transition Returns Bad Request |
| 10 | `debugLogIsWrittenForStatusValidation` | Debug Log Is Written For Status Validation |

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
