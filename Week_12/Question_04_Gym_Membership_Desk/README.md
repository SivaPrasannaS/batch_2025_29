# Question 04: Gym Membership Desk

**Week 12: Spring Security: In-Memory Auth** · UserDetailsService bean with an InMemoryUserDetailsManager holding two users

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
| 1 | `securityConfigDeclaresUserDetailsServiceBean` | Security Config Declares User Details Service Bean |
| 2 | `usersAreKeptInAnInMemoryUserDetailsManager` | Users Are Kept In An In Memory User Details Manager |
| 3 | `trainerUserHasTrainerRole` | Trainer User Has Trainer Role |
| 4 | `managerUserHasManagerRole` | Manager User Has Manager Role |
| 5 | `trainerCanListMembers` | Trainer Can List Members |
| 6 | `managerCanAddMember` | Manager Can Add Member |
| 7 | `addedMemberAppearsInList` | Added Member Appears In List |
| 8 | `requestWithoutCredentialsReturnsUnauthorized` | Request Without Credentials Returns Unauthorized |
| 9 | `wrongPasswordReturnsUnauthorized` | Wrong Password Returns Unauthorized |
| 10 | `unknownUserReturnsUnauthorized` | Unknown User Returns Unauthorized |

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
