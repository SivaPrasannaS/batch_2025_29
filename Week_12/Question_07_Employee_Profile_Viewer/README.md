# Question 07: Employee Profile Viewer

**Week 12: Spring Security: In-Memory Auth** · Reading the logged-in user from Authentication (a /me endpoint)

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
| 1 | `securityConfigUsesBCryptForInMemoryUsers` | Security Config Uses B Crypt For In Memory Users |
| 2 | `controllerReadsTheLoggedInUserFromAuthentication` | Controller Reads The Logged In User From Authentication |
| 3 | `meWithoutLoginReturnsUnauthorized` | Me Without Login Returns Unauthorized |
| 4 | `meReturnsTheLoggedInUsername` | Me Returns The Logged In Username |
| 5 | `meReturnsTheUserRoles` | Me Returns The User Roles |
| 6 | `savedProfileUsesTheLoggedInUsername` | Saved Profile Uses The Logged In Username |
| 7 | `myProfileReturnsTheSavedProfile` | My Profile Returns The Saved Profile |
| 8 | `eachUserSeesOnlyTheirOwnProfile` | Each User Sees Only Their Own Profile |
| 9 | `userWithoutProfileGetsNotFound` | User Without Profile Gets Not Found |
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
