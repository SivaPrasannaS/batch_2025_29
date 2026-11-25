# Question 02: Cafeteria Menu Portal

**Week 12: Spring Security: In-Memory Auth** · First SecurityFilterChain bean: public GET endpoints, protected POST, HTTP Basic

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
| 2 | `applicationPropertiesDefineTheStaffUser` | Application Properties Define The Staff User |
| 3 | `viewingMenuDoesNotNeedLogin` | Viewing Menu Does Not Need Login |
| 4 | `addingMenuItemWithoutLoginReturnsUnauthorized` | Adding Menu Item Without Login Returns Unauthorized |
| 5 | `addingMenuItemWithLoginReturnsCreated` | Adding Menu Item With Login Returns Created |
| 6 | `addedItemAppearsInPublicMenu` | Added Item Appears In Public Menu |
| 7 | `viewingOneMenuItemDoesNotNeedLogin` | Viewing One Menu Item Does Not Need Login |
| 8 | `wrongPasswordOnAddReturnsUnauthorized` | Wrong Password On Add Returns Unauthorized |
| 9 | `unknownMenuItemReturnsNotFound` | Unknown Menu Item Returns Not Found |
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
