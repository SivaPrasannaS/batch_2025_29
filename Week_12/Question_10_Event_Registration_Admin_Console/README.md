# Question 10: Event Registration Admin Console

**Week 12: Spring Security: In-Memory Auth** · Mini integration: public, participant, organizer and admin rules with BCrypt users

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
| 2 | `usersHaveRolesAndBCryptPasswords` | Users Have Roles And B Crypt Passwords |
| 3 | `registrationCountIsPublic` | Registration Count Is Public |
| 4 | `registeringWithoutLoginReturnsUnauthorized` | Registering Without Login Returns Unauthorized |
| 5 | `participantCanRegister` | Participant Can Register |
| 6 | `participantCannotListRegistrations` | Participant Cannot List Registrations |
| 7 | `organizerCanListRegistrations` | Organizer Can List Registrations |
| 8 | `organizerCannotDeleteRegistrations` | Organizer Cannot Delete Registrations |
| 9 | `adminCanDeleteRegistrations` | Admin Can Delete Registrations |
| 10 | `wrongPasswordReturnsUnauthorized` | Wrong Password Returns Unauthorized |

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
