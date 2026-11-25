# Question 01: Internship Portal Account Service

**Week 13: Spring Security: JWT – Part 1** · Database-backed users, registration with BCrypt and duplicate checks, and JWT creation at login

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding.zip` | The scaffolding folder below, zipped for upload |
| `scaffolding/springapp/` | Starter project: `pom.xml` (with jjwt), `application.properties` (with the JWT settings), main class and TODO stubs |
| `scaffolding/junit/` | Assessment kit: `junit.sh`, `Report/` and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing description as a Word document with every table as an image |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/*.png` | Structure, entity, DTO, JWT, security, method, endpoint and error tables as images |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `userEntityImplementsUserDetails` | User Entity Implements User Details |
| 2 | `customUserDetailsServiceLoadsUsersFromTheDatabase` | Custom User Details Service Loads Users From The Database |
| 3 | `registerReturnsCreatedStudentWithoutPassword` | Register Returns Created Student Without Password |
| 4 | `passwordIsStoredAsBCryptHash` | Password Is Stored As B Crypt Hash |
| 5 | `duplicateEmailReturnsConflict` | Duplicate Email Returns Conflict |
| 6 | `loginReturnsBearerTokenResponse` | Login Returns Bearer Token Response |
| 7 | `tokenIsSignedWithConfiguredSecretUsingHs256` | Token Is Signed With Configured Secret Using Hs256 |
| 8 | `tokenClaimsContainEmailRoleAndConfiguredExpiry` | Token Claims Contain Email Role And Configured Expiry |
| 9 | `wrongPasswordOrUnknownEmailReturnsUnauthorized` | Wrong Password Or Unknown Email Returns Unauthorized |
| 10 | `loginIsStatelessAndCreatesNoSessionCookie` | Login Is Stateless And Creates No Session Cookie |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

The testcases start the application on an H2 in-memory database (no MySQL needed for grading) and check JWTs with plain JDK code: they decode the header and claims, recompute the HS256 signature with `app.jwt.secret`.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
