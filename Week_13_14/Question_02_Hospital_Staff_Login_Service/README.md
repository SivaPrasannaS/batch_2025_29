# Question 02: Hospital Staff Login Service

**Week 13: Spring Security: JWT – Part 1** · Roles in the user entity and the token, controlled role assignment, seeded admin and clean 401 errors

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
| 1 | `roleEnumHasTheRequiredValues` | Role Enum Has The Required Values |
| 2 | `registerWithAllowedRoleReturnsCreated` | Register With Allowed Role Returns Created |
| 3 | `registeringAsAdminIsRejected` | Registering As Admin Is Rejected |
| 4 | `registeringWithUnknownRoleIsRejected` | Registering With Unknown Role Is Rejected |
| 5 | `duplicateEmailReturnsConflict` | Duplicate Email Returns Conflict |
| 6 | `loginTokenContainsRoleAndNameClaimsWithoutSession` | Login Token Contains Role And Name Claims Without Session |
| 7 | `seededAdminCanLoginWithAdminRole` | Seeded Admin Can Login With Admin Role |
| 8 | `tokenExpiryMatchesConfiguration` | Token Expiry Matches Configuration |
| 9 | `badCredentialsReturnJsonUnauthorized` | Bad Credentials Return Json Unauthorized |
| 10 | `emailAvailabilityEndpointIsPublic` | Email Availability Endpoint Is Public |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

The testcases start the application on an H2 in-memory database (no MySQL needed for grading) and check JWTs with plain JDK code: they decode the header and claims, recompute the HS256 signature with `app.jwt.secret`.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
