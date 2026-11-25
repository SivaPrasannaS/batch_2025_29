# Question 03: College Canteen Order Service

**Week 11: Logging & AOP Basics** · Log configuration: logging.file.name and logging.level per package

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
| 1 | `applicationPropertiesConfigureLogFile` | Application Properties Configure Log File |
| 2 | `applicationPropertiesSetRootAndServiceLevels` | Application Properties Set Root And Service Levels |
| 3 | `placeOrderReturnsCreatedWithTotal` | Place Order Returns Created With Total |
| 4 | `placeOrderInfoLineIsWrittenToLogFile` | Place Order Info Line Is Written To Log File |
| 5 | `serviceDebugLineIsWrittenToLogFile` | Service Debug Line Is Written To Log File |
| 6 | `controllerDebugLineIsNotLogged` | Controller Debug Line Is Not Logged |
| 7 | `serveOrderLogsInfoToLogFile` | Serve Order Logs Info To Log File |
| 8 | `unknownOrderLogsWarnToLogFile` | Unknown Order Logs Warn To Log File |
| 9 | `consoleAndFileShowTheSameInfoLine` | Console And File Show The Same Info Line |
| 10 | `getOrderByIdReturnsSavedOrder` | Get Order By Id Returns Saved Order |

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
