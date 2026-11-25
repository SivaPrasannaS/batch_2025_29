# Question 04: Online Course Management API

**Week 14: Spring Security: JWT – Part 2** · Role-based access with roles from the token, @PreAuthorize, 401 vs 403 handlers and CORS for React

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding.zip` | The scaffolding folder below, zipped for upload |
| `scaffolding/springapp/` | Starter project: the Week 13 part (users, register / login, token creation) provided complete; TODO stubs for token validation, the filter, error handlers, security configuration and the domain classes |
| `scaffolding/junit/` | Assessment kit: `junit.sh`, `Report/` and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing description as a Word document with every table as an image |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/*.png` | Structure, entity, DTO, JWT, security, method, endpoint and error tables as images |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `methodSecurityAndAccessDeniedHandlerAreConfigured` | Method Security And Access Denied Handler Are Configured |
| 2 | `courseListIsPublic` | Course List Is Public |
| 3 | `registeredUserGetsStudentRoleInToken` | Registered User Gets Student Role In Token |
| 4 | `instructorCanCreateCourse` | Instructor Can Create Course |
| 5 | `studentCannotCreateCourse` | Student Cannot Create Course |
| 6 | `anonymousCreateReturnsUnauthorizedJson` | Anonymous Create Returns Unauthorized Json |
| 7 | `onlyAdminCanDeleteCourses` | Only Admin Can Delete Courses |
| 8 | `tokenWithForgedRoleIsRejected` | Token With Forged Role Is Rejected |
| 9 | `corsAllowsTheReactFrontend` | Cors Allows The React Frontend |
| 10 | `corsRejectsOtherOrigins` | Cors Rejects Other Origins |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

The testcases start the application on an H2 in-memory database (no MySQL needed for grading) and check JWTs with plain JDK code: they decode the header and claims, recompute the HS256 signature with `app.jwt.secret`, and sign their own expired, tampered and forged tokens.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
