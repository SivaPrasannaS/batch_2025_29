# Question 02: Student Bus Pass Renewal Portal

**Week 11: Logging & AOP Basics** · Choosing log levels (DEBUG, INFO, WARN, ERROR) and parameterized log messages

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
| 1 | `busPassServiceImplDeclaresStaticSlf4jLogger` | Bus Pass Service Impl Declares Static Slf4j Logger |
| 2 | `applicationPropertiesEnableDebugForBasePackage` | Application Properties Enable Debug For Base Package |
| 3 | `issuePassReturnsCreatedAndLogsInfo` | Issue Pass Returns Created And Logs Info |
| 4 | `renewPassAddsMonths` | Renew Pass Adds Months |
| 5 | `renewPassLogsDebugBeforeRenewal` | Renew Pass Logs Debug Before Renewal |
| 6 | `renewPassLogsInfoWithMonthsRemaining` | Renew Pass Logs Info With Months Remaining |
| 7 | `renewUnknownPassLogsWarnAndReturnsNotFound` | Renew Unknown Pass Logs Warn And Returns Not Found |
| 8 | `renewWithInvalidMonthsLogsErrorAndReturnsBadRequest` | Renew With Invalid Months Logs Error And Returns Bad Request |
| 9 | `successfulRenewalLogsNoWarnOrError` | Successful Renewal Logs No Warn Or Error |
| 10 | `renewWithTwelveMonthsIsAccepted` | Renew With Twelve Months Is Accepted |

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
