# Week 12: Spring Security: In-Memory Auth: Assessment Set

Ten beginner-level Spring Boot assessment questions for Week 12 of the course planner. Each question is a small API (2–4 endpoints, one entity) with one new security idea: controller → service → service/impl → JpaRepository → entity, plus `config/SecurityConfig`.

## Planner topics covered

1. Security fundamentals: authentication vs authorization; `spring-boot-starter-security`; the default user
2. Security filter chain: `SecurityFilterChain` bean (lambda DSL); form login vs HTTP Basic
3. In-memory users: `UserDetails`, `UserDetailsService`, `InMemoryUserDetailsManager`
4. Password encoding: `PasswordEncoder`, `BCryptPasswordEncoder`

## Questions

| # | Project | Focus | Base URL |
|---|---|---|---|
| 01 | [College Notice Board API](Question_01_College_Notice_Board_API/) | Adding spring-boot-starter-security and a login user from application.properties | /api/notices |
| 02 | [Cafeteria Menu Portal](Question_02_Cafeteria_Menu_Portal/) | First SecurityFilterChain bean: public GET endpoints, protected POST, HTTP Basic | /api/menu |
| 03 | [Placement Cell Login Portal](Question_03_Placement_Cell_Login_Portal/) | Form login and HTTP Basic in the same application | /api/drives |
| 04 | [Gym Membership Desk](Question_04_Gym_Membership_Desk/) | UserDetailsService bean with an InMemoryUserDetailsManager holding two users | /api/members |
| 05 | [Movie Review Board](Question_05_Movie_Review_Board/) | Authorization by role in the filter chain: USER can read, ADMIN can write (401 vs 403) | /api/reviews |
| 06 | [Hospital Visitor Pass System](Question_06_Hospital_Visitor_Pass_System/) | PasswordEncoder bean (BCryptPasswordEncoder) and in-memory users with encoded passwords | /api/visitor-passes |
| 07 | [Employee Profile Viewer](Question_07_Employee_Profile_Viewer/) | Reading the logged-in user from Authentication (a /me endpoint) | /api |
| 08 | [School Marks Portal](Question_08_School_Marks_Portal/) | Three roles and a simple access matrix | /api/marks |
| 09 | [Password Safety Checker](Question_09_Password_Safety_Checker/) | Using the PasswordEncoder bean: encode and match passwords with BCrypt | /api/passwords |
| 10 | [Event Registration Admin Console](Question_10_Event_Registration_Admin_Console/) | Mini integration: public, participant, organizer and admin rules with BCrypt users | /api/registrations |

## Folder layout (same for every question)

```text
Question_NN_Project_Title/
├── scaffolding.zip            zipped scaffolding folder
├── scaffolding/
│   ├── springapp/             starter project for students
│   └── junit/                 junit.sh, Report/, test/java/.../SpringappApplicationTests.java
├── Solution/springapp/        reference solution (+ tests in src/test)
├── Description/               Question_Description.docx / .md and all table images
└── README.md
```

## Technical baseline

- Java 17, Spring Boot 3.3.5, Maven, base package `com.examly.springapp`, root project `springapp`
- MySQL in the projects (`mysql-connector-j`; `jdbc:mysql://localhost:3306/<db>?createDatabaseIfNotExist=true`, user `root` / password `examly`); the testcases use H2 (test scope). Question 9 has no database.
- `spring-boot-starter-security` in every project; in Question 1 the students add it themselves (it is left out of the scaffolding `pom.xml`).
- Testcases use only JDK and JUnit types, call the API with HTTP Basic, read the security beans from the running app through reflection and report every unexpected exception as FAIL.

## Verification summary

- 10 questions × 10 testcases = **100 testcases**
- All 100 pass against the reference solutions
- All 10 scaffoldings compile and score 0 / 10 with no compilation or runtime errors
