# Question 06: Hospital Doctor Directory

**Week 10: Validation & Exception Handling** · Custom DuplicateResourceException mapped to 409 Conflict, together with validation and 404 handling

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
| 1 | `duplicateResourceExceptionExtendsRuntimeException` | Duplicate Resource Exception Extends Runtime Exception |
| 2 | `doctorRepositoryDeclaresExistsByLicenseNumber` | Doctor Repository Declares Exists By License Number |
| 3 | `globalExceptionHandlerHandlesDuplicateResourceException` | Global Exception Handler Handles Duplicate Resource Exception |
| 4 | `addDoctorWithValidDataReturnsCreated` | Add Doctor With Valid Data Returns Created |
| 5 | `addDoctorWithDuplicateLicenseReturnsConflict` | Add Doctor With Duplicate License Returns Conflict |
| 6 | `conflictErrorResponseFollowsStandardFormat` | Conflict Error Response Follows Standard Format |
| 7 | `updateDoctorWithAnotherDoctorsLicenseReturnsConflict` | Update Doctor With Another Doctors License Returns Conflict |
| 8 | `updateDoctorKeepingOwnLicenseReturnsOk` | Update Doctor Keeping Own License Returns Ok |
| 9 | `addDoctorWithInvalidLicenseFormatReturnsBadRequest` | Add Doctor With Invalid License Format Returns Bad Request |
| 10 | `getDoctorsBySpecializationReturnsOnlyMatchingDoctors` | Get Doctors By Specialization Returns Only Matching Doctors |

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
