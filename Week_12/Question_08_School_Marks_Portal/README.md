# Question 08: School Marks Portal

**Week 12: Spring Security: In-Memory Auth** · Three roles and a simple access matrix

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding.zip` | The scaffolding folder below, zipped for upload |
| `scaffolding/springapp/` | Starter project given to students: `pom.xml`, `application.properties`, main class and TODO stubs |
| `scaffolding/junit/` | Assessment kit: `junit.sh`, `Report/` and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing description as a Word document with every table as an image |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/*.png` | Project structure, entity, security, method, endpoint and status-code tables as images |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `securityConfigDeclaresFilterChainUsersAndEncoder` | Security Config Declares Filter Chain Users And Encoder |
| 2 | `usersHaveTheRequiredRolesAndHashedPasswords` | Users Have The Required Roles And Hashed Passwords |
| 3 | `studentCanReadMarks` | Student Can Read Marks |
| 4 | `studentCannotAddMarks` | Student Cannot Add Marks |
| 5 | `teacherCanAddMarks` | Teacher Can Add Marks |
| 6 | `principalCanAddMarks` | Principal Can Add Marks |
| 7 | `teacherCannotDeleteMarks` | Teacher Cannot Delete Marks |
| 8 | `principalCanDeleteMarks` | Principal Can Delete Marks |
| 9 | `anonymousRequestReturnsUnauthorized` | Anonymous Request Returns Unauthorized |
| 10 | `deletingUnknownMarkReturnsNotFound` | Deleting Unknown Mark Returns Not Found |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

The testcases start the application on an H2 in-memory database, so no MySQL server is needed for grading. Running the application itself (`mvn spring-boot:run`) uses the MySQL settings in `application.properties`.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
- Negative checks (401 / 403) first confirm that the correct credentials work, so an empty project cannot pass them.
