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
 * Job Portal Applicant Profiles - Week 10 (Validation and Exception Handling) assessment tests.
 *
 * Every testcase ends as PASS or FAIL:
 * - This file uses only JDK and JUnit types. Student classes, Spring Boot and
 *   JSON handling are reached through reflection or built-in helpers, so a
 *   missing or renamed student class can never cause a compilation error here.
 * - The application is started on a background thread with a time limit and
 *   inside a try block, so a broken or hanging application becomes a FAIL.
 * - Any unexpected exception thrown by a testcase is reported as a FAIL with
 *   the exception name and message (see FailOnUnexpectedError).
 */
@ExtendWith(SpringappApplicationTests.FailOnUnexpectedError.class)
public class SpringappApplicationTests {

    private static final String BASE_PACKAGE = "com.examly.springapp";
    private static final String MAIN_CLASS = BASE_PACKAGE + ".SpringappApplication";
    private static final String HANDLER_CLASS = BASE_PACKAGE + ".exception.GlobalExceptionHandler";
    private static final String NOT_FOUND_EXCEPTION = BASE_PACKAGE + ".exception.ResourceNotFoundException";
    private static final long STARTUP_TIMEOUT_MILLIS = 120_000;

    private static final AtomicInteger COUNTER = new AtomicInteger(1000);

    private static HttpClient httpClient;
    private static Object context;
    private static String rootUrl;
    private static String startupError = "the application was not started";

    @BeforeAll
    static void startApplication() {
        try {
            String[] args = {
                    "--server.port=0",
                    "--spring.main.banner-mode=off",
                    "--spring.datasource.url=jdbc:h2:mem:jobportaltestdb;DB_CLOSE_DELAY=-1",
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
    }

    private static final String REQUEST_DTO = BASE_PACKAGE + ".dto.ApplicantRequest";
    private static final String APPLICANTS = "/api/applicants";

    @Test
    void applicantRequestUsesAllSevenConstraintTypes() {
        Class<?> dto = loadClass(REQUEST_DTO);
        List<String> found = new ArrayList<>();
        for (Field field : dto.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                found.add(annotation.annotationType().getSimpleName());
            }
        }
        for (String constraint : new String[] { "NotNull", "NotBlank", "Size", "Email", "Min", "Max", "Pattern" }) {
            assertTrue(found.contains(constraint), "ApplicantRequest must use the @" + constraint + " constraint");
        }
    }

    @Test
    void applicantRequestExpectedSalaryHasRangeConstraint() {
        Class<?> dto = loadClass(REQUEST_DTO);
        assertConstraint(dto, "expectedSalary", "NotNull");
        assertRange(dto, "expectedSalary", 10000, 500000);
    }

    @Test
    void createApplicantWithValidDataReturnsCreated() {
        requireRunningApplication();
        ApiResponse response = post(APPLICANTS, applicant("Deepika Rao", "deepika.rao@mail.com", "9876501234",
                "B.E. Computer Science", 2, 45000, "Java, Spring Boot, MySQL"));
        assertEquals(201, response.status, "POST /api/applicants with valid data must return 201");
        JsonNode body = parse(response.body);
        assertTrue(body.path("id").asLong(0) > 0, "Created applicant must contain a generated id");
        assertEquals(45000, body.path("expectedSalary").asInt(-1), "expectedSalary must match the request");
    }

    @Test
    void createApplicantWithEmptyBodyReportsAllRequiredFields() {
        requireRunningApplication();
        ApiResponse response = post(APPLICANTS, json());
        assertEquals(400, response.status, "An empty request body must return 400");
        JsonNode errors = parse(response.body).path("fieldErrors");
        for (String name : new String[] { "fullName", "email", "phone", "highestQualification", "experienceYears",
                "expectedSalary", "skills" }) {
            assertTrue(errors.has(name), "fieldErrors must report the missing field " + name);
        }
    }

    @Test
    void createApplicantWithSalaryBelowMinimumReturnsBadRequest() {
        requireRunningApplication();
        ApiResponse response = post(APPLICANTS, applicant("Manoj Pillai", "manoj.p@mail.com", "9123409876",
                "B.Sc. Physics", 0, 9999, "Excel, Communication"));
        assertEquals(400, response.status, "An expected salary of 9999 must return 400");
        assertEquals("Expected salary must be at least 10000", fieldError(response, "expectedSalary"),
                "fieldErrors.expectedSalary must contain the @Min message");
    }

    @Test
    void createApplicantAtSalaryUpperBoundaryReturnsCreated() {
        requireRunningApplication();
        ApiResponse response = post(APPLICANTS, applicant("Sunita Joshi", "sunita.j@mail.com", "9988776655",
                "MBA", 40, 500000, "Leadership, Strategy"));
        assertEquals(201, response.status, "Experience 40 and expected salary 500000 are valid boundary values");
    }

    @Test
    void createApplicantWithTooLongSkillsReturnsBadRequest() {
        requireRunningApplication();
        String skills = "S".repeat(201);
        ApiResponse response = post(APPLICANTS, applicant("Ajay Nair", "ajay.nair@mail.com", "9012345678",
                "M.Tech", 5, 90000, skills));
        assertEquals(400, response.status, "Skills longer than 200 characters must return 400");
        assertEquals("Skills must not exceed 200 characters", fieldError(response, "skills"),
                "fieldErrors.skills must contain the @Size message");
    }

    @Test
    void createApplicantWithMalformedJsonReturnsBadRequest() {
        requireRunningApplication();
        ApiResponse response = send("POST", APPLICANTS, "{\"fullName\": \"Broken Json\", ");
        assertEquals(400, response.status, "A malformed JSON body must return 400");
        JsonNode body = parse(response.body);
        assertStandardError(body, 400, "Bad Request", APPLICANTS);
        assertEquals("Malformed JSON request", body.path("message").asText(),
                "Malformed JSON error message must be Malformed JSON request");
    }

    @Test
    void updateApplicantWithInvalidEmailReturnsBadRequest() {
        requireRunningApplication();
        long id = createAndGetId(APPLICANTS, applicant("Pooja Hegde", "pooja.h@mail.com", "9090909090",
                "BCA", 1, 30000, "HTML, CSS, JavaScript"));
        ApiResponse response = send("PUT", APPLICANTS + "/" + id, applicant("Pooja Hegde", "pooja.h-at-mail.com",
                "9090909090", "BCA", 1, 30000, "HTML, CSS, JavaScript"));
        assertEquals(400, response.status, "Updating with an invalid email must return 400");
        assertEquals("Email must be a valid email address", fieldError(response, "email"),
                "fieldErrors.email must contain the @Email message");
    }

    @Test
    void deleteUnknownApplicantReturnsNotFound() {
        requireRunningApplication();
        ApiResponse response = send("DELETE", APPLICANTS + "/666666", null);
        assertEquals(404, response.status, "DELETE /api/applicants/{id} for a missing applicant must return 404");
        JsonNode body = parse(response.body);
        assertStandardError(body, 404, "Not Found", APPLICANTS + "/666666");
        assertEquals("Applicant not found with id: 666666", body.path("message").asText(),
                "404 response must contain the not-found message");
    }

    private static Map<String, Object> applicant(String name, String email, String phone, String qualification,
            Integer experience, Integer salary, String skills) {
        return json("fullName", name, "email", email, "phone", phone, "highestQualification", qualification,
                "experienceYears", experience, "expectedSalary", salary, "skills", skills);
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
