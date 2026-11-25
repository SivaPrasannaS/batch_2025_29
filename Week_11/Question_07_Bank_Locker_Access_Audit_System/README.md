# Question 07: Bank Locker Access Audit System

**Week 11: Logging & AOP Basics** · Audit-trail aspect: @AfterReturning saves an audit record after successful operations

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
| 1 | `auditAspectHasAfterReturningAdviceWithReturningParameter` | Audit Aspect Has After Returning Advice With Returning Parameter |
| 2 | `auditLogEntityDeclaresRequiredFields` | Audit Log Entity Declares Required Fields |
| 3 | `allocateLockerCreatesAuditEntry` | Allocate Locker Creates Audit Entry |
| 4 | `openLockerRecordsAuditAndIncrementsAccessCount` | Open Locker Records Audit And Increments Access Count |
| 5 | `closeLockerRecordsAuditEntry` | Close Locker Records Audit Entry |
| 6 | `readOperationsAreNotAudited` | Read Operations Are Not Audited |
| 7 | `failedOperationIsNotAudited` | Failed Operation Is Not Audited |
| 8 | `auditEntriesHaveTimestamp` | Audit Entries Have Timestamp |
| 9 | `auditAspectLogsInfoLine` | Audit Aspect Logs Info Line |
| 10 | `duplicateLockerReturnsConflictWithoutAuditEntry` | Duplicate Locker Returns Conflict Without Audit Entry |

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
