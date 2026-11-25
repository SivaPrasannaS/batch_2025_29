# Question 07: Job Portal Applicant Profiles

**Week 10: Validation & Exception Handling** · All seven constraint annotations on one DTO, reporting every field error at once, and handling malformed JSON

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
| 1 | `applicantRequestUsesAllSevenConstraintTypes` | Applicant Request Uses All Seven Constraint Types |
| 2 | `applicantRequestExpectedSalaryHasRangeConstraint` | Applicant Request Expected Salary Has Range Constraint |
| 3 | `createApplicantWithValidDataReturnsCreated` | Create Applicant With Valid Data Returns Created |
| 4 | `createApplicantWithEmptyBodyReportsAllRequiredFields` | Create Applicant With Empty Body Reports All Required Fields |
| 5 | `createApplicantWithSalaryBelowMinimumReturnsBadRequest` | Create Applicant With Salary Below Minimum Returns Bad Request |
| 6 | `createApplicantAtSalaryUpperBoundaryReturnsCreated` | Create Applicant At Salary Upper Boundary Returns Created |
| 7 | `createApplicantWithTooLongSkillsReturnsBadRequest` | Create Applicant With Too Long Skills Returns Bad Request |
| 8 | `createApplicantWithMalformedJsonReturnsBadRequest` | Create Applicant With Malformed Json Returns Bad Request |
| 9 | `updateApplicantWithInvalidEmailReturnsBadRequest` | Update Applicant With Invalid Email Returns Bad Request |
| 10 | `deleteUnknownApplicantReturnsNotFound` | Delete Unknown Applicant Returns Not Found |

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
