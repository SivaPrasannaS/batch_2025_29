# Question 05: Movie Review Board

**Week 12: Spring Security: In-Memory Auth** · Authorization by role in the filter chain: USER can read, ADMIN can write (401 vs 403)

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
| 1 | `securityConfigDeclaresFilterChainAndUsers` | Security Config Declares Filter Chain And Users |
| 2 | `usersHaveTheRequiredRoles` | Users Have The Required Roles |
| 3 | `viewerCanReadReviews` | Viewer Can Read Reviews |
| 4 | `moderatorCanReadReviews` | Moderator Can Read Reviews |
| 5 | `viewerCannotAddReview` | Viewer Cannot Add Review |
| 6 | `moderatorCanAddReview` | Moderator Can Add Review |
| 7 | `addingReviewWithoutLoginReturnsUnauthorized` | Adding Review Without Login Returns Unauthorized |
| 8 | `viewerCannotDeleteReview` | Viewer Cannot Delete Review |
| 9 | `moderatorDeletesReview` | Moderator Deletes Review |
| 10 | `deletingUnknownReviewReturnsNotFound` | Deleting Unknown Review Returns Not Found |

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
