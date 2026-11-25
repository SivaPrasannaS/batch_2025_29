# Question 01: Campus Library Membership Portal

**Week 10: Validation & Exception Handling** · Bean Validation basics: @NotNull, @NotBlank, @Size with @Valid and field-level errors

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
| 1 | `memberRequestFullNameHasNotBlankAndSizeConstraints` | Member Request Full Name Has Not Blank And Size Constraints |
| 2 | `memberRequestMaxBooksAllowedHasNotNullConstraint` | Member Request Max Books Allowed Has Not Null Constraint |
| 3 | `globalExceptionHandlerIsAnnotatedAsControllerAdvice` | Global Exception Handler Is Annotated As Controller Advice |
| 4 | `createMemberWithValidDataReturnsCreated` | Create Member With Valid Data Returns Created |
| 5 | `createMemberWithBlankFullNameReturnsBadRequest` | Create Member With Blank Full Name Returns Bad Request |
| 6 | `createMemberWithShortStudentIdReturnsBadRequest` | Create Member With Short Student Id Returns Bad Request |
| 7 | `createMemberWithoutMaxBooksAllowedReturnsBadRequest` | Create Member Without Max Books Allowed Returns Bad Request |
| 8 | `validationErrorResponseFollowsStandardFormat` | Validation Error Response Follows Standard Format |
| 9 | `createMemberWithMultipleInvalidFieldsReportsEveryField` | Create Member With Multiple Invalid Fields Reports Every Field |
| 10 | `getMemberByUnknownIdReturnsNotFoundError` | Get Member By Unknown Id Returns Not Found Error |

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
