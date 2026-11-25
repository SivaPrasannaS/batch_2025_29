# Question 03: Placement Cell Login Portal

**Week 12: Spring Security: In-Memory Auth** · Form login and HTTP Basic in the same application

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
| 1 | `securityConfigDeclaresSecurityFilterChainBean` | Security Config Declares Security Filter Chain Bean |
| 2 | `loginPageIsAvailable` | Login Page Is Available |
| 3 | `browserRequestWithoutLoginRedirectsToLoginPage` | Browser Request Without Login Redirects To Login Page |
| 4 | `apiRequestWithoutLoginReturnsUnauthorized` | Api Request Without Login Returns Unauthorized |
| 5 | `httpBasicLoginReturnsSampleDrives` | Http Basic Login Returns Sample Drives |
| 6 | `formLoginWithValidCredentialsSucceeds` | Form Login With Valid Credentials Succeeds |
| 7 | `formLoginWithWrongPasswordRedirectsToErrorPage` | Form Login With Wrong Password Redirects To Error Page |
| 8 | `formLoginSessionCanCallTheApi` | Form Login Session Can Call The Api |
| 9 | `getDriveByIdWithBasicLoginReturnsDrive` | Get Drive By Id With Basic Login Returns Drive |
| 10 | `unknownDriveReturnsNotFound` | Unknown Drive Returns Not Found |

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
