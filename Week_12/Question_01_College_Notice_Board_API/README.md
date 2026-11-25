# Question 01: College Notice Board API

**Week 12: Spring Security: In-Memory Auth** · Adding spring-boot-starter-security and a login user from application.properties

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
| 1 | `securityStarterIsAddedToTheProject` | Security Starter Is Added To The Project |
| 2 | `applicationPropertiesDefineTheLoginUser` | Application Properties Define The Login User |
| 3 | `listNoticesWithoutLoginReturnsUnauthorized` | List Notices Without Login Returns Unauthorized |
| 4 | `listNoticesWithCorrectLoginReturnsSampleNotices` | List Notices With Correct Login Returns Sample Notices |
| 5 | `wrongPasswordReturnsUnauthorized` | Wrong Password Returns Unauthorized |
| 6 | `unknownUserReturnsUnauthorized` | Unknown User Returns Unauthorized |
| 7 | `getNoticeByIdWithLoginReturnsNotice` | Get Notice By Id With Login Returns Notice |
| 8 | `getNoticeByIdWithoutLoginReturnsUnauthorized` | Get Notice By Id Without Login Returns Unauthorized |
| 9 | `unknownNoticeReturnsNotFoundForLoggedInUser` | Unknown Notice Returns Not Found For Logged In User |
| 10 | `unauthorizedResponseAsksForBasicCredentials` | Unauthorized Response Asks For Basic Credentials |

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
