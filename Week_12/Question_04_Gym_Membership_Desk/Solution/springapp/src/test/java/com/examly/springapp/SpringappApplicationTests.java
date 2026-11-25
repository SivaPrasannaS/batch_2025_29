package com.examly.springapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

/**
 * Gym Membership Desk - Week 12 (Spring Security: In-Memory Auth) assessment tests.
 *
 * Every testcase ends as PASS or FAIL:
 * - This file uses only JDK and JUnit types. Student classes, Spring Boot and
 *   JSON handling are reached through reflection or built-in helpers, so a
 *   missing or renamed student class can never cause a compilation error here.
 * - The application is started on a background thread with a time limit and
 *   inside a try block, so a broken or hanging application becomes a FAIL.
 * - Log output is checked by mirroring System.out (where Logback writes the
 *   console log) and by reading the configured log file.
 * - Any unexpected exception thrown by a testcase is reported as a FAIL with
 *   the exception name and message (see FailOnUnexpectedError).
 */
@ExtendWith(SpringappApplicationTests.FailOnUnexpectedError.class)
public class SpringappApplicationTests {

    private static final String BASE_PACKAGE = "com.examly.springapp";
    private static final String MAIN_CLASS = BASE_PACKAGE + ".SpringappApplication";
    private static final long STARTUP_TIMEOUT_MILLIS = 120_000;

    private static final AtomicInteger COUNTER = new AtomicInteger(1000);

    private static HttpClient httpClient;
    private static Object context;
    private static String rootUrl;
    private static String startupError = "the application was not started";
    private static final java.io.ByteArrayOutputStream CAPTURED = new java.io.ByteArrayOutputStream();
    private static java.io.PrintStream originalOut;

    @BeforeAll
    static void startApplication() {
        startCapturingOutput();
        try {
            String[] args = {
                    "--server.port=0",
                    "--spring.main.banner-mode=off",
                    "--spring.datasource.url=jdbc:h2:mem:gymtestdb;DB_CLOSE_DELAY=-1",
                    "--spring.datasource.driver-class-name=org.h2.Driver",
                    "--spring.datasource.username=sa",
                    "--spring.datasource.password=",
                    "--spring.jpa.hibernate.ddl-auto=create-drop" };
            Object[] started = new Object[1];
            Throwable[] failure = new Throwable[1];
            Thread starter = new Thread(() -> {
                try {
                    Class<?> mainClass = Class.forName(MAIN_CLASS);
                    Class<?> springApplication = Class.forName("org.springframework.boot.SpringApplication");
                    Object app = springApplication.getConstructor(Class[].class)
                            .newInstance((Object) new Class<?>[] { mainClass });
                    started[0] = springApplication.getMethod("run", String[].class).invoke(app, (Object) args);
                } catch (Throwable t) {
                    failure[0] = t;
                }
            }, "assessment-app-starter");
            starter.setDaemon(true);
            starter.start();
            starter.join(STARTUP_TIMEOUT_MILLIS);
            if (starter.isAlive()) {
                startupError = "the application did not start within " + (STARTUP_TIMEOUT_MILLIS / 1000) + " seconds";
                return;
            }
            if (failure[0] != null) {
                startupError = describe(failure[0]);
                return;
            }
            Object env = Class.forName("org.springframework.context.ConfigurableApplicationContext")
                    .getMethod("getEnvironment").invoke(started[0]);
            Object port = Class.forName("org.springframework.core.env.PropertyResolver")
                    .getMethod("getProperty", String.class).invoke(env, "local.server.port");
            if (port == null) {
                startupError = "the application started without an embedded web server";
                closeQuietly(started[0]);
                return;
            }
            context = started[0];
            rootUrl = "http://localhost:" + port;
        } catch (Throwable t) {
            context = null;
            startupError = describe(t);
        }
    }

    @AfterAll
    static void stopApplication() {
        closeQuietly(context);
        stopCapturingOutput();
    }

    private static final String CONFIG = BASE_PACKAGE + ".config.SecurityConfig";
    private static final String MEMBERS = "/api/members";

    @Test
    void securityConfigDeclaresUserDetailsServiceBean() {
        Class<?> config = loadClass(CONFIG);
        assertSecurityConfig(config);
        assertNotNull(beanMethodReturning(config, "org.springframework.security.core.userdetails.UserDetailsService"),
                "SecurityConfig must declare a @Bean method that returns UserDetailsService");
    }

    @Test
    void usersAreKeptInAnInMemoryUserDetailsManager() {
        Object service = beanOfType("org.springframework.security.core.userdetails.UserDetailsService");
        assertEquals("org.springframework.security.provisioning.InMemoryUserDetailsManager", service.getClass().getName(),
                "The UserDetailsService bean must be an InMemoryUserDetailsManager");
        assertEquals("trainer", invoke(loadUser("trainer"), "getUsername"), "The in-memory manager must contain the user trainer");
    }

    @Test
    void trainerUserHasTrainerRole() {
        assertEquals(List.of("ROLE_TRAINER"), authoritiesOf(loadUser("trainer")), "User trainer must have role TRAINER");
    }

    @Test
    void managerUserHasManagerRole() {
        assertEquals(List.of("ROLE_MANAGER"), authoritiesOf(loadUser("manager")), "User manager must have role MANAGER");
    }

    @Test
    void trainerCanListMembers() {
        assertEquals(200, getAs(MEMBERS, "trainer", "trainer@123").status, "trainer / trainer@123 must be able to call GET /api/members");
    }

    @Test
    void managerCanAddMember() {
        HttpResult result = postAs(MEMBERS, member("Ravi Kumar"), "manager", "manager@123");
        assertEquals(201, result.status, "manager / manager@123 must be able to call POST /api/members");
        assertTrue(parse(result.body).path("id").asLong(0) > 0, "The created member must have a generated id");
    }

    @Test
    void addedMemberAppearsInList() {
        String name = "Member" + next();
        createAs(MEMBERS, member(name), "trainer", "trainer@123");
        assertTrue(getAs(MEMBERS, "manager", "manager@123").body.contains(name), "A newly added member must appear in the list");
    }

    @Test
    void requestWithoutCredentialsReturnsUnauthorized() {
        assertEquals(200, getAs(MEMBERS, "trainer", "trainer@123").status, "trainer / trainer@123 must be able to call GET /api/members");
        assertEquals(401, getAs(MEMBERS, null, null).status, "GET /api/members without credentials must return 401");
    }

    @Test
    void wrongPasswordReturnsUnauthorized() {
        assertEquals(200, getAs(MEMBERS, "trainer", "trainer@123").status, "trainer / trainer@123 must be able to call GET /api/members");
        assertEquals(401, getAs(MEMBERS, "trainer", "manager@123").status, "A wrong password must return 401");
    }

    @Test
    void unknownUserReturnsUnauthorized() {
        assertEquals(200, getAs(MEMBERS, "trainer", "trainer@123").status, "trainer / trainer@123 must be able to call GET /api/members");
        assertEquals(401, getAs(MEMBERS, "user", "password").status, "An unknown username must return 401");
    }

    private static Map<String, Object> member(String name) {
        return json("memberName", name, "planName", "Gold", "monthlyFee", 1200.0);
    }

    // ------------------------------------------------------------------
    // Log capture helpers (Week 11)
    // ------------------------------------------------------------------

    /** Copies everything written to System.out into CAPTURED while still printing it. */
    static final class TeeStream extends java.io.OutputStream {
        private final java.io.PrintStream original;

        TeeStream(java.io.PrintStream original) {
            this.original = original;
        }

        @Override
        public void write(int b) {
            original.write(b);
            synchronized (CAPTURED) {
                CAPTURED.write(b);
            }
        }

        @Override
        public void write(byte[] bytes, int offset, int length) {
            original.write(bytes, offset, length);
            synchronized (CAPTURED) {
                CAPTURED.write(bytes, offset, length);
            }
        }

        @Override
        public void flush() {
            original.flush();
        }
    }

    private static void startCapturingOutput() {
        try {
            if (originalOut == null) {
                originalOut = System.out;
                System.setOut(new java.io.PrintStream(new TeeStream(originalOut), true, "UTF-8"));
            }
        } catch (Throwable ignored) {
            // output capture is best effort; log checks will then fail with a clear message
        }
    }

    private static void stopCapturingOutput() {
        try {
            if (originalOut != null) {
                System.setOut(originalOut);
            }
        } catch (Throwable ignored) {
            // nothing to restore
        }
    }

    private static int logMark() {
        synchronized (CAPTURED) {
            return CAPTURED.size();
        }
    }

    private static String logsSince(int mark) {
        try {
            Thread.sleep(150);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        byte[] all;
        synchronized (CAPTURED) {
            all = CAPTURED.toByteArray();
        }
        int from = Math.max(0, Math.min(mark, all.length));
        return new String(all, from, all.length - from, java.nio.charset.StandardCharsets.UTF_8)
                .replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private static boolean hasLevel(String line, String level) {
        return java.util.regex.Pattern.compile("(^|\\s)" + level + "(\\s|$)").matcher(line).find();
    }

    /** First line in the text that contains the message, has the level and mentions the logger class. */
    private static String findLine(String text, String level, String loggerClass, String message) {
        for (String line : text.split("\\R")) {
            if (line.contains(message) && (level == null || hasLevel(line, level))
                    && (loggerClass == null || line.contains(loggerClass))) {
                return line;
            }
        }
        return null;
    }

    private static String findLineMatching(String text, String level, String regex) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        for (String line : text.split("\\R")) {
            if (pattern.matcher(line).find() && (level == null || hasLevel(line, level))) {
                return line;
            }
        }
        return null;
    }

    private static void assertLogged(int mark, String level, String loggerClass, String message) {
        String logs = logsSince(mark);
        if (findLine(logs, level, loggerClass, message) == null) {
            String nearest = findLine(logs, null, null, message);
            fail("Expected a " + level + " log line from " + loggerClass + " containing: \"" + message + "\""
                    + (nearest != null ? " (found it with the wrong level or logger: " + nearest.trim() + ")" : ""));
        }
    }

    private static void assertLoggedMatching(int mark, String level, String regex, String description) {
        if (findLineMatching(logsSince(mark), level, regex) == null) {
            fail("Expected a " + level + " log line " + description);
        }
    }

    private static void assertNotLogged(int mark, String message, String why) {
        String line = findLine(logsSince(mark), null, null, message);
        if (line != null) {
            fail(why + " (unexpected log line: " + line.trim() + ")");
        }
    }

    private static int indexOfLine(String text, String message) {
        int index = 0;
        for (String line : text.split("\\R")) {
            if (line.contains(message)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private static String readProjectFile(String relativePath) {
        try {
            return java.nio.file.Files.readString(java.nio.file.Paths.get(relativePath));
        } catch (Throwable t) {
            fail("Could not read " + relativePath + " (expected inside the springapp project)");
            return null;
        }
    }

    private static java.util.Properties applicationProperties() {
        java.util.Properties properties = new java.util.Properties();
        try (java.io.Reader reader = java.nio.file.Files.newBufferedReader(
                java.nio.file.Paths.get("src/main/resources/application.properties"))) {
            properties.load(reader);
        } catch (Throwable t) {
            fail("Could not read src/main/resources/application.properties");
        }
        return properties;
    }

    private static long fileLength(String relativePath) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(relativePath);
            return java.nio.file.Files.exists(path) ? java.nio.file.Files.size(path) : 0;
        } catch (Throwable t) {
            return 0;
        }
    }

    private static String fileContentSince(String relativePath, long offset) {
        try {
            Thread.sleep(150);
            java.nio.file.Path path = java.nio.file.Paths.get(relativePath);
            if (!java.nio.file.Files.exists(path)) {
                fail("Log file " + relativePath + " was not created");
            }
            byte[] all = java.nio.file.Files.readAllBytes(path);
            int from = (int) Math.max(0, Math.min(offset, all.length));
            return new String(all, from, all.length - from, java.nio.charset.StandardCharsets.UTF_8);
        } catch (AssertionError e) {
            throw e;
        } catch (Throwable t) {
            fail("Could not read log file " + relativePath + ": " + t.getMessage());
            return null;
        }
    }

    private static boolean hasStaticLogger(Class<?> type) {
        try {
            for (Field field : type.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (field.getType().getName().equals("org.slf4j.Logger")
                        && java.lang.reflect.Modifier.isStatic(modifiers)
                        && java.lang.reflect.Modifier.isFinal(modifiers)) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
            // treated as missing
        }
        return false;
    }

    private static List<Method> adviceMethods(Class<?> aspect, String annotationSimpleName) {
        List<Method> methods = new ArrayList<>();
        try {
            for (Method method : aspect.getDeclaredMethods()) {
                if (findAnnotation(method, "org.aspectj.lang.annotation." + annotationSimpleName) != null) {
                    methods.add(method);
                }
            }
        } catch (Throwable ignored) {
            // treated as none
        }
        return methods;
    }

    private static String adviceExpression(Method method, String annotationSimpleName) {
        Annotation annotation = findAnnotation(method, "org.aspectj.lang.annotation." + annotationSimpleName);
        if (annotation == null) {
            return "";
        }
        String value = "";
        try {
            value = String.valueOf(annotation.annotationType().getMethod("value").invoke(annotation));
        } catch (Throwable ignored) {
            // no value attribute
        }
        try {
            String pointcut = String.valueOf(annotation.annotationType().getMethod("pointcut").invoke(annotation));
            if (!pointcut.isEmpty()) {
                value = pointcut;
            }
        } catch (Throwable ignored) {
            // no pointcut attribute
        }
        return value;
    }

    private static void assertAspectComponent(Class<?> aspect) {
        assertTrue(hasAnnotation(aspect, "org.aspectj.lang.annotation.Aspect"),
                aspect.getSimpleName() + " must be annotated with @Aspect");
        assertTrue(hasAnnotation(aspect, "org.springframework.stereotype.Component"),
                aspect.getSimpleName() + " must be annotated with @Component");
    }

    private static boolean takesParameter(Method method, String typeName) {
        for (Class<?> type : method.getParameterTypes()) {
            if (type.getName().equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Security helpers (Week 12)
    // ------------------------------------------------------------------

    /** Response with headers, used for authenticated requests. */
    private static final class HttpResult {
        private final int status;
        private final String body;
        private final Map<String, List<String>> headers;

        private HttpResult(int status, String body, Map<String, List<String>> headers) {
            this.status = status;
            this.body = body;
            this.headers = headers;
        }

        private String header(String name) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(name) && !entry.getValue().isEmpty()) {
                    return entry.getValue().get(0);
                }
            }
            return "";
        }
    }

    private static HttpResult sendAs(String method, String path, Object body, String user, String password, String accept) {
        requireRunningApplication();
        try {
            String payload = body == null ? null : (body instanceof String ? (String) body : JsonNode.write(body));
            HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(rootUrl + path))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json")
                    .header("Accept", accept == null ? "application/json" : accept)
                    .method(method, payload == null ? HttpRequest.BodyPublishers.noBody()
                            : HttpRequest.BodyPublishers.ofString(payload));
            if (user != null) {
                String token = java.util.Base64.getEncoder().encodeToString(
                        (user + ":" + password).getBytes(java.nio.charset.StandardCharsets.UTF_8));
                builder.header("Authorization", "Basic " + token);
            }
            if (httpClient == null) {
                httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            }
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            return new HttpResult(response.statusCode(), response.body(), response.headers().map());
        } catch (Throwable t) {
            fail("HTTP " + method + " " + path + " failed: " + t.getMessage());
            return null;
        }
    }

    private static HttpResult getAs(String path, String user, String password) {
        return sendAs("GET", path, null, user, password, null);
    }

    private static HttpResult postAs(String path, Object body, String user, String password) {
        return sendAs("POST", path, body, user, password, null);
    }

    private static HttpResult deleteAs(String path, String user, String password) {
        return sendAs("DELETE", path, null, user, password, null);
    }

    private static long createAs(String path, Object body, String user, String password) {
        HttpResult result = postAs(path, body, user, password);
        if (result.status != 201 && result.status != 200) {
            fail("Could not create a record through POST " + path + " as " + user + ", status was " + result.status);
        }
        long id = parse(result.body).path("id").asLong(0);
        if (id <= 0) {
            fail("POST " + path + " did not return a generated id");
        }
        return id;
    }

    private static void assertBasicChallenge(HttpResult result) {
        assertTrue(result.header("WWW-Authenticate").startsWith("Basic"),
                "A 401 response must ask for HTTP Basic credentials (WWW-Authenticate: Basic ...)");
    }

    /** Looks up a bean of the given type from the running application context. */
    private static Object beanOfType(String className) {
        requireRunningApplication();
        try {
            Class<?> type = Class.forName(className);
            return context.getClass().getMethod("getBean", Class.class).invoke(context, type);
        } catch (Throwable t) {
            fail("The application must define a bean of type " + className.substring(className.lastIndexOf('.') + 1));
            return null;
        }
    }

    private static Object invoke(Object target, String method, Object... args) {
        try {
            for (Method m : target.getClass().getMethods()) {
                if (m.getName().equals(method) && m.getParameterCount() == args.length) {
                    return m.invoke(target, args);
                }
            }
            fail("Method " + method + " not found on " + target.getClass().getSimpleName());
        } catch (AssertionError e) {
            throw e;
        } catch (Throwable t) {
            Throwable cause = t instanceof InvocationTargetException ? ((InvocationTargetException) t).getTargetException() : t;
            fail(method + " failed: " + cause.getClass().getSimpleName() + ": " + cause.getMessage());
        }
        return null;
    }

    private static Object loadUser(String username) {
        Object service = beanOfType("org.springframework.security.core.userdetails.UserDetailsService");
        return invoke(service, "loadUserByUsername", username);
    }

    private static List<String> authoritiesOf(Object userDetails) {
        List<String> names = new ArrayList<>();
        Object authorities = invoke(userDetails, "getAuthorities");
        if (authorities instanceof Collection) {
            for (Object authority : (Collection<?>) authorities) {
                names.add(String.valueOf(invoke(authority, "getAuthority")));
            }
        }
        Collections.sort(names);
        return names;
    }

    private static Method beanMethodReturning(Class<?> config, String returnTypeName) {
        try {
            for (Method method : config.getDeclaredMethods()) {
                if (method.getReturnType().getName().equals(returnTypeName)
                        && hasAnnotation(method, "org.springframework.context.annotation.Bean")) {
                    return method;
                }
            }
        } catch (Throwable ignored) {
            // treated as missing
        }
        return null;
    }

    private static void assertSecurityConfig(Class<?> config) {
        assertTrue(hasAnnotation(config, "org.springframework.context.annotation.Configuration"),
                config.getSimpleName() + " must be annotated with @Configuration");
        assertNotNull(beanMethodReturning(config, "org.springframework.security.web.SecurityFilterChain"),
                config.getSimpleName() + " must declare a @Bean method that returns SecurityFilterChain");
    }

    private static boolean isBcryptHash(String value) {
        return value != null && value.matches("^\\$2[aby]?\\$\\d{2}\\$[./A-Za-z0-9]{53}$");
    }

    // ------------------------------------------------------------------
    // Common helpers
    // ------------------------------------------------------------------

    private static int next() {
        return COUNTER.incrementAndGet();
    }

    private static Map<String, Object> json(Object... keyValues) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < keyValues.length; i += 2) {
            map.put(String.valueOf(keyValues[i]), keyValues[i + 1]);
        }
        return map;
    }

    private static Class<?> loadClass(String name) {
        try {
            return Class.forName(name, false, SpringappApplicationTests.class.getClassLoader());
        } catch (Throwable t) {
            fail("Class not found: " + name);
            return null;
        }
    }

    private static Field requireField(Class<?> type, String name) {
        try {
            return type.getDeclaredField(name);
        } catch (Throwable t) {
            fail(type.getSimpleName() + " must declare a field named " + name);
            return null;
        }
    }

    private static Method findMethod(Class<?> type, String name, Class<?>... params) {
        try {
            return type.getMethod(name, params);
        } catch (Throwable t) {
            return null;
        }
    }

    private static Annotation findAnnotation(AnnotatedElement element, String annotationName) {
        try {
            for (Annotation annotation : element.getAnnotations()) {
                if (annotation.annotationType().getName().equals(annotationName)) {
                    return annotation;
                }
            }
        } catch (Throwable ignored) {
            // treated as missing annotation
        }
        return null;
    }

    private static boolean hasAnnotation(AnnotatedElement element, String annotationName) {
        return findAnnotation(element, annotationName) != null;
    }

    private static Object annotationValue(Annotation annotation, String attribute) {
        try {
            return annotation.annotationType().getMethod(attribute).invoke(annotation);
        } catch (Throwable t) {
            fail("Could not read attribute " + attribute + " of @" + annotation.annotationType().getSimpleName());
            return null;
        }
    }

    private static void assertConstraint(Class<?> type, String fieldName, String constraint) {
        Field field = requireField(type, fieldName);
        assertTrue(hasAnnotation(field, "jakarta.validation.constraints." + constraint),
                type.getSimpleName() + "." + fieldName + " must be annotated with @" + constraint);
    }

    private static void assertRange(Class<?> type, String fieldName, long min, long max) {
        Field field = requireField(type, fieldName);
        Annotation minAnnotation = findAnnotation(field, "jakarta.validation.constraints.Min");
        Annotation maxAnnotation = findAnnotation(field, "jakarta.validation.constraints.Max");
        assertNotNull(minAnnotation, type.getSimpleName() + "." + fieldName + " must be annotated with @Min");
        assertNotNull(maxAnnotation, type.getSimpleName() + "." + fieldName + " must be annotated with @Max");
        assertEquals(min, annotationValue(minAnnotation, "value"), "@Min on " + fieldName + " must be " + min);
        assertEquals(max, annotationValue(maxAnnotation, "value"), "@Max on " + fieldName + " must be " + max);
    }

    private static String patternOf(Class<?> type, String fieldName) {
        Annotation pattern = findAnnotation(requireField(type, fieldName), "jakarta.validation.constraints.Pattern");
        assertNotNull(pattern, type.getSimpleName() + "." + fieldName + " must be annotated with @Pattern");
        return String.valueOf(annotationValue(pattern, "regexp"));
    }

    private static boolean isControllerAdvice(Class<?> type) {
        return hasAnnotation(type, "org.springframework.web.bind.annotation.RestControllerAdvice")
                || hasAnnotation(type, "org.springframework.web.bind.annotation.ControllerAdvice");
    }

    private static boolean handlesException(Class<?> handler, String exceptionName) {
        try {
            for (Method method : handler.getDeclaredMethods()) {
                Annotation annotation = findAnnotation(method,
                        "org.springframework.web.bind.annotation.ExceptionHandler");
                if (annotation == null) {
                    continue;
                }
                List<Class<?>> handled = new ArrayList<>();
                Object value = annotationValue(annotation, "value");
                if (value instanceof Class<?>[]) {
                    handled.addAll(List.of((Class<?>[]) value));
                }
                if (handled.isEmpty()) {
                    handled.addAll(List.of(method.getParameterTypes()));
                }
                for (Class<?> type : handled) {
                    if (type.getName().equals(exceptionName)) {
                        return true;
                    }
                }
            }
        } catch (Throwable ignored) {
            // treated as not handled
        }
        return false;
    }

    private static void assertRuntimeExceptionWithMessageConstructor(String className) {
        Class<?> type = loadClass(className);
        assertTrue(RuntimeException.class.isAssignableFrom(type),
                type.getSimpleName() + " must extend RuntimeException");
        try {
            type.getConstructor(String.class);
        } catch (Throwable t) {
            fail(type.getSimpleName() + " must declare a public constructor that accepts a String message");
        }
    }

    private static void assertStandardError(JsonNode body, int status, String error, String path) {
        assertFalse(body.path("timestamp").asText("").isBlank(), "Error response must contain a timestamp");
        assertEquals(status, body.path("status").asInt(-1), "Error response status must be " + status);
        assertEquals(error, body.path("error").asText(), "Error response error must be " + error);
        assertFalse(body.path("message").asText("").isBlank(), "Error response must contain a message");
        assertEquals(path, body.path("path").asText(), "Error response path must be the request URI");
    }

    private static String fieldError(ApiResponse response, String field) {
        return parse(response.body).path("fieldErrors").path(field).asText("");
    }

    private static long createAndGetId(String path, Object body) {
        ApiResponse response = send("POST", path, body);
        if (response.status != 201 && response.status != 200) {
            fail("Could not create a record through POST " + path + ", status was " + response.status
                    + ", body: " + response.body);
        }
        long id = parse(response.body).path("id").asLong(0);
        if (id <= 0) {
            fail("POST " + path + " did not return a generated id");
        }
        return id;
    }

    private static void requireRunningApplication() {
        if (context == null || rootUrl == null) {
            fail("Application failed to start: " + startupError);
        }
    }

    private static String describe(Throwable t) {
        Throwable root = t;
        int depth = 0;
        while (depth++ < 20) {
            Throwable cause = root instanceof InvocationTargetException
                    ? ((InvocationTargetException) root).getTargetException() : root.getCause();
            if (cause == null || cause == root) {
                break;
            }
            root = cause;
        }
        return t.getClass().getSimpleName() + (root != t ? " caused by " + root.getClass().getSimpleName() : "")
                + ": " + root.getMessage();
    }

    private static void closeQuietly(Object closeable) {
        try {
            if (closeable instanceof AutoCloseable) {
                ((AutoCloseable) closeable).close();
            }
        } catch (Throwable ignored) {
            // nothing to clean up
        }
    }

    private static JsonNode parse(String json) {
        try {
            return JsonNode.parse(json == null || json.isBlank() ? "{}" : json);
        } catch (Throwable t) {
            fail("Response is not valid JSON: " + json);
            return null;
        }
    }

    private static ApiResponse post(String path, Object body) {
        return send("POST", path, body);
    }

    private static ApiResponse get(String path) {
        return send("GET", path, null);
    }

    private static ApiResponse send(String method, String path, Object body) {
        requireRunningApplication();
        try {
            String payload = body == null ? null : (body instanceof String ? (String) body : JsonNode.write(body));
            HttpRequest.BodyPublisher publisher = payload == null
                    ? HttpRequest.BodyPublishers.noBody()
                    : HttpRequest.BodyPublishers.ofString(payload);
            HttpRequest request = HttpRequest.newBuilder(URI.create(rootUrl + path))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .method(method, publisher)
                    .build();
            if (httpClient == null) {
                httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            }
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());
        } catch (Throwable t) {
            fail("HTTP " + method + " " + path + " failed: " + t.getMessage());
            return null;
        }
    }

    private static final class ApiResponse {
        private final int status;
        private final String body;

        private ApiResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }

    /**
     * Turns every unexpected exception into an assertion failure so that a testcase
     * always ends as PASS or FAIL and never as an error.
     */
    static final class FailOnUnexpectedError implements TestExecutionExceptionHandler,
            LifecycleMethodExecutionExceptionHandler {

        @Override
        public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable)
                throws Throwable {
            if (throwable instanceof AssertionError) {
                throw throwable;
            }
            throw new AssertionError("Unexpected " + describe(throwable), throwable);
        }

        @Override
        public void handleBeforeAllMethodExecutionException(ExtensionContext extensionContext, Throwable throwable) {
            context = null;
            startupError = describe(throwable);
        }

        @Override
        public void handleAfterAllMethodExecutionException(ExtensionContext extensionContext, Throwable throwable) {
            // shutting down must never affect the results
        }
    }

    /** Minimal JSON reader / writer so the testcases depend only on the JDK and JUnit. */
    static final class JsonNode implements Iterable<JsonNode> {
        private static final JsonNode MISSING = new JsonNode(null, true);
        private final Object value;
        private final boolean missing;

        private JsonNode(Object value, boolean missing) {
            this.value = value;
            this.missing = missing;
        }

        static JsonNode parse(String text) {
            Reader reader = new Reader(text);
            Object value = reader.readValue();
            reader.skipWhitespace();
            if (reader.pos != text.length()) {
                throw new IllegalArgumentException("Unexpected trailing content");
            }
            return new JsonNode(value, false);
        }

        JsonNode path(String field) {
            if (value instanceof Map && ((Map<?, ?>) value).containsKey(field)) {
                return new JsonNode(((Map<?, ?>) value).get(field), false);
            }
            return MISSING;
        }

        boolean has(String field) {
            return value instanceof Map && ((Map<?, ?>) value).containsKey(field);
        }

        boolean isArray() {
            return value instanceof List;
        }

        boolean isObject() {
            return value instanceof Map;
        }

        int size() {
            if (value instanceof List) {
                return ((List<?>) value).size();
            }
            return value instanceof Map ? ((Map<?, ?>) value).size() : 0;
        }

        String asText() {
            if (!missing && value == null) {
                return "null";
            }
            return asText("");
        }

        String asText(String defaultValue) {
            if (missing || value == null || value instanceof Map || value instanceof List) {
                return defaultValue;
            }
            return String.valueOf(value);
        }

        int asInt() {
            return asInt(0);
        }

        int asInt(int defaultValue) {
            Double number = number();
            return number == null ? defaultValue : number.intValue();
        }

        long asLong(long defaultValue) {
            if (value instanceof Long) {
                return (Long) value;
            }
            Double number = number();
            return number == null ? defaultValue : number.longValue();
        }

        double asDouble(double defaultValue) {
            Double number = number();
            return number == null ? defaultValue : number;
        }

        private Double number() {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            if (value instanceof String) {
                try {
                    return Double.parseDouble(((String) value).trim());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Iterator<JsonNode> iterator() {
            Collection<?> items = value instanceof List ? (List<?>) value
                    : value instanceof Map ? ((Map<?, ?>) value).values() : Collections.emptyList();
            List<JsonNode> nodes = new ArrayList<>();
            for (Object item : items) {
                nodes.add(new JsonNode(item, false));
            }
            return nodes.iterator();
        }

        static String write(Object value) {
            StringBuilder out = new StringBuilder();
            writeValue(value, out);
            return out.toString();
        }

        private static void writeValue(Object value, StringBuilder out) {
            if (value == null) {
                out.append("null");
            } else if (value instanceof String) {
                writeString((String) value, out);
            } else if (value instanceof Number || value instanceof Boolean) {
                out.append(value);
            } else if (value instanceof Map) {
                out.append('{');
                boolean first = true;
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                    if (!first) {
                        out.append(',');
                    }
                    first = false;
                    writeString(String.valueOf(entry.getKey()), out);
                    out.append(':');
                    writeValue(entry.getValue(), out);
                }
                out.append('}');
            } else if (value instanceof Collection) {
                out.append('[');
                boolean first = true;
                for (Object item : (Collection<?>) value) {
                    if (!first) {
                        out.append(',');
                    }
                    first = false;
                    writeValue(item, out);
                }
                out.append(']');
            } else {
                writeString(String.valueOf(value), out);
            }
        }

        private static void writeString(String text, StringBuilder out) {
            out.append('"');
            for (char c : text.toCharArray()) {
                switch (c) {
                    case '"': out.append("\\\""); break;
                    case '\\': out.append("\\\\"); break;
                    case '\n': out.append("\\n"); break;
                    case '\r': out.append("\\r"); break;
                    case '\t': out.append("\\t"); break;
                    default:
                        if (c < 0x20) {
                            out.append(String.format("\\u%04x", (int) c));
                        } else {
                            out.append(c);
                        }
                }
            }
            out.append('"');
        }

        private static final class Reader {
            private final String text;
            private int pos;

            private Reader(String text) {
                this.text = text;
            }

            private void skipWhitespace() {
                while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) {
                    pos++;
                }
            }

            private char peek() {
                skipWhitespace();
                if (pos >= text.length()) {
                    throw new IllegalArgumentException("Unexpected end of JSON");
                }
                return text.charAt(pos);
            }

            private Object readValue() {
                char c = peek();
                if (c == '{') {
                    return readObject();
                }
                if (c == '[') {
                    return readArray();
                }
                if (c == '"') {
                    return readString();
                }
                if (text.startsWith("true", pos)) {
                    pos += 4;
                    return Boolean.TRUE;
                }
                if (text.startsWith("false", pos)) {
                    pos += 5;
                    return Boolean.FALSE;
                }
                if (text.startsWith("null", pos)) {
                    pos += 4;
                    return null;
                }
                return readNumber();
            }

            private Map<String, Object> readObject() {
                Map<String, Object> map = new LinkedHashMap<>();
                pos++;
                if (peek() == '}') {
                    pos++;
                    return map;
                }
                while (true) {
                    if (peek() != '"') {
                        throw new IllegalArgumentException("Expected a field name");
                    }
                    String key = readString();
                    if (peek() != ':') {
                        throw new IllegalArgumentException("Expected ':'");
                    }
                    pos++;
                    map.put(key, readValue());
                    char c = peek();
                    pos++;
                    if (c == '}') {
                        return map;
                    }
                    if (c != ',') {
                        throw new IllegalArgumentException("Expected ',' or '}'");
                    }
                }
            }

            private List<Object> readArray() {
                List<Object> list = new ArrayList<>();
                pos++;
                if (peek() == ']') {
                    pos++;
                    return list;
                }
                while (true) {
                    list.add(readValue());
                    char c = peek();
                    pos++;
                    if (c == ']') {
                        return list;
                    }
                    if (c != ',') {
                        throw new IllegalArgumentException("Expected ',' or ']'");
                    }
                }
            }

            private String readString() {
                StringBuilder out = new StringBuilder();
                pos++;
                while (pos < text.length()) {
                    char c = text.charAt(pos++);
                    if (c == '"') {
                        return out.toString();
                    }
                    if (c == '\\') {
                        char e = text.charAt(pos++);
                        switch (e) {
                            case 'b': out.append('\b'); break;
                            case 'f': out.append('\f'); break;
                            case 'n': out.append('\n'); break;
                            case 'r': out.append('\r'); break;
                            case 't': out.append('\t'); break;
                            case 'u': out.append((char) Integer.parseInt(text.substring(pos, pos + 4), 16)); pos += 4; break;
                            default: out.append(e);
                        }
                    } else {
                        out.append(c);
                    }
                }
                throw new IllegalArgumentException("Unterminated string");
            }

            private Number readNumber() {
                int start = pos;
                while (pos < text.length() && "+-0123456789.eE".indexOf(text.charAt(pos)) >= 0) {
                    pos++;
                }
                String number = text.substring(start, pos);
                if (number.isEmpty()) {
                    throw new IllegalArgumentException("Unexpected character at position " + start);
                }
                if (number.contains(".") || number.contains("e") || number.contains("E")) {
                    return Double.parseDouble(number);
                }
                return Long.parseLong(number);
            }
        }
    }
}
