# Question 03: Personal Expense Tracker API

**Week 14: Spring Security: JWT – Part 2** · Validating JWTs on every request with a OncePerRequestFilter and a 401 JSON entry point

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding.zip` | The scaffolding folder below, zipped for upload |
| `scaffolding/springapp/` | Starter project: the Week 13 part (users, register / login, token creation) provided complete; TODO stubs for token validation, the filter, error handlers, security configuration and the domain classes |
| `scaffolding/junit/` | Assessment kit: `junit.sh`, `Report/` and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing description as a Word document with every table as an image |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/*.png` | Structure, entity, DTO, JWT, security, method, endpoint and error tables as images |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `jwtFilterExtendsOncePerRequestFilter` | Jwt Filter Extends Once Per Request Filter |
| 2 | `entryPointImplementsAuthenticationEntryPoint` | Entry Point Implements Authentication Entry Point |
| 3 | `jwtServiceExtractsAndValidatesTokens` | Jwt Service Extracts And Validates Tokens |
| 4 | `addExpenseWithValidTokenReturnsCreated` | Add Expense With Valid Token Returns Created |
| 5 | `usersSeeOnlyTheirOwnExpenses` | Users See Only Their Own Expenses |
| 6 | `requestWithoutTokenReturnsUnauthorizedJson` | Request Without Token Returns Unauthorized Json |
| 7 | `malformedTokenReturnsUnauthorized` | Malformed Token Returns Unauthorized |
| 8 | `tamperedTokenReturnsUnauthorized` | Tampered Token Returns Unauthorized |
| 9 | `expiredTokenReturnsUnauthorized` | Expired Token Returns Unauthorized |
| 10 | `validlySignedTokenForUnknownUserReturnsUnauthorized` | Validly Signed Token For Unknown User Returns Unauthorized |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

The testcases start the application on an H2 in-memory database (no MySQL needed for grading) and check JWTs with plain JDK code: they decode the header and claims, recompute the HS256 signature with `app.jwt.secret`, and sign their own expired, tampered and forged tokens.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
