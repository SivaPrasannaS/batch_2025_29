# Question 01: Hostel Room Allocation System

**Week 11: Logging & AOP Basics** · SLF4J Logger and LoggerFactory instead of System.out; INFO logging for normal operations

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
| 1 | `roomServiceImplDeclaresStaticSlf4jLogger` | Room Service Impl Declares Static Slf4j Logger |
| 2 | `roomServiceImplUsesLoggerFactoryInsteadOfSystemOut` | Room Service Impl Uses Logger Factory Instead Of System Out |
| 3 | `createRoomReturnsCreatedWithZeroOccupants` | Create Room Returns Created With Zero Occupants |
| 4 | `createRoomLogsInfoWithIdAndNumber` | Create Room Logs Info With Id And Number |
| 5 | `getAllRoomsLogsNumberOfRooms` | Get All Rooms Logs Number Of Rooms |
| 6 | `getRoomByIdLogsInfoMessage` | Get Room By Id Logs Info Message |
| 7 | `allocateBedIncreasesOccupants` | Allocate Bed Increases Occupants |
| 8 | `allocateBedLogsOccupancy` | Allocate Bed Logs Occupancy |
| 9 | `allocateBedInFullRoomReturnsBadRequest` | Allocate Bed In Full Room Returns Bad Request |
| 10 | `getUnknownRoomReturnsNotFoundWithoutSuccessLog` | Get Unknown Room Returns Not Found Without Success Log |

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
