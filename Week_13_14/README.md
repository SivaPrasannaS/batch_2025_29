# Weeks 13–14: Spring Security with JWT: Assessment Set

Four Spring Boot assessment questions: two for Week 13 (JWT Part 1: stateless authentication, database users, registration and token creation) and two for Week 14 (JWT Part 2: token validation, the JWT filter, role-based access and 401 / 403 handling). The reference repository `SivaPrasannaS/spring-security` was used for implementation patterns; refresh tokens, token revocation, permission authorities and Swagger are left out because they are not in the planner.

## Questions

| # | Week | Project | Focus |
|---|---|---|---|
| 01 | Week 13 | [Internship Portal Account Service](Question_01_Internship_Portal_Account_Service/) | Database-backed users, registration with BCrypt and duplicate checks, and JWT creation at login |
| 02 | Week 13 | [Hospital Staff Login Service](Question_02_Hospital_Staff_Login_Service/) | Roles in the user entity and the token, controlled role assignment, seeded admin and clean 401 errors |
| 03 | Week 14 | [Personal Expense Tracker API](Question_03_Personal_Expense_Tracker_API/) | Validating JWTs on every request with a OncePerRequestFilter and a 401 JSON entry point |
| 04 | Week 14 | [Online Course Management API](Question_04_Online_Course_Management_API/) | Role-based access with roles from the token, @PreAuthorize, 401 vs 403 handlers and CORS for React |

## Common structure

- `model/User` (implements `UserDetails`) and `enumerated/Role`; `repository/UserRepository`
- `dto/RegisterRequest`, `LoginRequest`, `AuthResponse`, `UserResponse`; `exception` package with `ErrorResponse` and `GlobalExceptionHandler`
- `service/AuthService` and `service/JwtService` with their `service/impl` classes, plus `service/impl/CustomUserDetailsService`
- `config/ApplicationConfig` (PasswordEncoder, AuthenticationProvider, AuthenticationManager) and `config/SecurityConfig`; Week 14 adds `JwtAuthenticationFilter`, `JwtAuthenticationEntryPoint` and (Q4) `JwtAccessDeniedHandler`
- `controller/AuthController` (`/api/auth/register`, `/api/auth/login`) plus a domain controller in Week 14

## Technical baseline

- Java 17, Spring Boot 3.3.5, jjwt 0.12.6, base package `com.examly.springapp`, root project `springapp`
- MySQL in the projects (user `root` / password `examly`, database created automatically); the testcases use H2 (test scope)
- JWT settings in `application.properties`: `app.jwt.secret` (Base64, 256-bit) and `app.jwt.expiration-ms`
- Week 14 scaffoldings provide the Week 13 part complete so students focus on validation, the filter and authorization

## Folder layout

```text
Question_NN_Project_Title/
├── scaffolding.zip
├── scaffolding/
│   ├── springapp/
│   └── junit/
├── Solution/springapp/
├── Description/   Question_Description.docx / .md and all table images
└── README.md
```

## Verification summary

- 4 questions × 10 testcases = **40 testcases**, all passing against the reference solutions
- All 4 scaffoldings compile and score 0 / 10 with no compilation or runtime errors
