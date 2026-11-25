# Question 09: Password Safety Checker

**Week 12: Spring Security: In-Memory Auth** · Using the PasswordEncoder bean: encode and match passwords with BCrypt

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
| 1 | `securityConfigDeclaresBCryptPasswordEncoder` | Security Config Declares B Crypt Password Encoder |
| 2 | `analystPasswordIsStoredAsHash` | Analyst Password Is Stored As Hash |
| 3 | `infoEndpointIsPublic` | Info Endpoint Is Public |
| 4 | `encodeRequiresLogin` | Encode Requires Login |
| 5 | `encodeReturnsBCryptHash` | Encode Returns B Crypt Hash |
| 6 | `encodingTheSamePasswordTwiceGivesDifferentHashes` | Encoding The Same Password Twice Gives Different Hashes |
| 7 | `matchesReturnsTrueForTheCorrectPassword` | Matches Returns True For The Correct Password |
| 8 | `matchesReturnsFalseForAWrongPassword` | Matches Returns False For A Wrong Password |
| 9 | `encodedPasswordDoesNotContainThePlainText` | Encoded Password Does Not Contain The Plain Text |
| 10 | `matchesRequiresLogin` | Matches Requires Login |

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
