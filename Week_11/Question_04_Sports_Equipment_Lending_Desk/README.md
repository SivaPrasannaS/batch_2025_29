# Question 04: Sports Equipment Lending Desk

**Week 11: Logging & AOP Basics** · First aspect: @Aspect, @Component and @Before with an execution() pointcut

## Folder contents

| Folder / File | Purpose |
|---|---|
| `scaffolding/springapp/` | Starter project given to students: `pom.xml`, `application.properties`, main class and TODO stubs for every required class |
| `scaffolding/junit/` | Assessment kit next to the student project: `junit.sh`, `Report/` (custom report) and `test/java/com/examly/springapp/SpringappApplicationTests.java` |
| `Solution/springapp/` | Complete reference solution (includes the testcase file under `src/test`) |
| `Description/Question_Description.docx` | Student-facing question description as a Word document with every table as an image |
| `Description/Question_Description.md` | Same description in Markdown |
| `Description/*.png` | Project structure, entity details, API endpoints and every other description table as images |

## Testcases (10)

| # | Testcase | Checks |
|---|---|---|
| 1 | `loggingAspectDeclaresStaticSlf4jLogger` | Logging Aspect Declares Static Slf4j Logger |
| 2 | `loggingAspectIsAnnotatedAsAspectAndComponent` | Logging Aspect Is Annotated As Aspect And Component |
| 3 | `loggingAspectHasBeforeAdviceWithExecutionPointcut` | Logging Aspect Has Before Advice With Execution Pointcut |
| 4 | `addEquipmentReturnsCreatedWithFullAvailability` | Add Equipment Returns Created With Full Availability |
| 5 | `beforeAdviceLogsAddEquipmentCall` | Before Advice Logs Add Equipment Call |
| 6 | `beforeAdviceLogsLendArguments` | Before Advice Logs Lend Arguments |
| 7 | `lendEquipmentReducesAvailableQuantity` | Lend Equipment Reduces Available Quantity |
| 8 | `beforeAdviceRunsEvenWhenLendingFails` | Before Advice Runs Even When Lending Fails |
| 9 | `returnEquipmentIncreasesAvailableAndIsLogged` | Return Equipment Increases Available And Is Logged |
| 10 | `beforeAdviceRunsOnceForEachServiceCall` | Before Advice Runs Once For Each Service Call |

## Running the testcases locally

```powershell
cd Solution/springapp
mvn test
```

In the assessment environment, `scaffolding/junit/junit.sh` copies `junit/test` into `springapp/src`, runs `mvn -q test` and prints the PASS/FAIL report through `Report/customReport.js`.

## Verification

- Reference solution: **10 / 10 testcases pass**
- Scaffolding: compiles; **0 / 10 pass** (every testcase reports a clean FAIL with no compilation error)
- Log checks read the console output (captured from `System.out`, where Logback writes) and, where configured, the log file under `logs/`.
- The testcase file uses only JDK and JUnit types and reports every unexpected exception as FAIL, so each testcase ends as PASS or FAIL.
