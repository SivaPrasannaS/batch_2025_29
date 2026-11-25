# Question 06: Courier Parcel Tracking Service

**Week 11: Logging & AOP Basics** · @Around advice measuring execution time, with results exposed through a metrics endpoint

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
| 1 | `performanceAspectHasAroundAdviceWithExecutionPointcut` | Performance Aspect Has Around Advice With Execution Pointcut |
| 2 | `aroundAdviceTakesProceedingJoinPointAndReturnsObject` | Around Advice Takes Proceeding Join Point And Returns Object |
| 3 | `bookParcelReturnsCreatedWithTrackingNumber` | Book Parcel Returns Created With Tracking Number |
| 4 | `aroundAdviceLogsExecutionTime` | Around Advice Logs Execution Time |
| 5 | `metricsCountEveryTrackParcelCall` | Metrics Count Every Track Parcel Call |
| 6 | `metricsTimesAreConsistent` | Metrics Times Are Consistent |
| 7 | `updateStatusChangesParcelStatus` | Update Status Changes Parcel Status |
| 8 | `failedCallIsStillMeasuredAndKeepsItsError` | Failed Call Is Still Measured And Keeps Its Error |
| 9 | `metricsForUnknownMethodReturnNotFound` | Metrics For Unknown Method Return Not Found |
| 10 | `metricsListIsSortedByMethodName` | Metrics List Is Sorted By Method Name |

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
