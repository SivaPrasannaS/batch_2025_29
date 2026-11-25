# Question 06: Hospital Visitor Pass System

**Week 12: Spring Security: In-Memory Auth** · PasswordEncoder bean (BCryptPasswordEncoder) and in-memory users with encoded passwords

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
| 1 | `securityConfigDeclaresPasswordEncoderBean` | Security Config Declares Password Encoder Bean |
| 2 | `passwordEncoderIsBCrypt` | Password Encoder Is B Crypt |
| 3 | `receptionPasswordIsStoredAsBCryptHash` | Reception Password Is Stored As B Crypt Hash |
| 4 | `securityPasswordIsStoredAsBCryptHash` | Security Password Is Stored As B Crypt Hash |
| 5 | `storedHashMatchesThePlainPassword` | Stored Hash Matches The Plain Password |
| 6 | `receptionCanCreateVisitorPass` | Reception Can Create Visitor Pass |
| 7 | `securityUserCanListVisitorPasses` | Security User Can List Visitor Passes |
| 8 | `requestWithoutCredentialsReturnsUnauthorized` | Request Without Credentials Returns Unauthorized |
| 9 | `wrongPasswordReturnsUnauthorized` | Wrong Password Returns Unauthorized |
| 10 | `createdPassAppearsInList` | Created Pass Appears In List |

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
