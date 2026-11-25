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
 * Hospital Doctor Directory - Week 10 (Validation and Exception Handling) assessment tests.
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
                    "--spring.datasource.url=jdbc:h2:mem:hospitaltestdb;DB_CLOSE_DELAY=-1",
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

    private static final String DUPLICATE_EXCEPTION = BASE_PACKAGE + ".exception.DuplicateResourceException";
    private static final String REPOSITORY_CLASS = BASE_PACKAGE + ".repository.DoctorRepository";
    private static final String DOCTORS = "/api/doctors";

    @Test
    void duplicateResourceExceptionExtendsRuntimeException() {
        assertRuntimeExceptionWithMessageConstructor(DUPLICATE_EXCEPTION);
    }

    @Test
    void doctorRepositoryDeclaresExistsByLicenseNumber() {
        Class<?> repository = loadClass(REPOSITORY_CLASS);
        Method method = findMethod(repository, "existsByLicenseNumber", String.class);
        assertNotNull(method, "DoctorRepository must declare existsByLicenseNumber(String licenseNumber)");
        assertTrue(method.getReturnType() == boolean.class || method.getReturnType() == Boolean.class,
                "existsByLicenseNumber must return boolean");
    }

    @Test
    void globalExceptionHandlerHandlesDuplicateResourceException() {
        Class<?> handler = loadClass(HANDLER_CLASS);
        assertTrue(isControllerAdvice(handler),
                "GlobalExceptionHandler must be annotated with @RestControllerAdvice or @ControllerAdvice");
        assertTrue(handlesException(handler, DUPLICATE_EXCEPTION),
                "GlobalExceptionHandler must declare an @ExceptionHandler method for DuplicateResourceException");
    }

    @Test
    void addDoctorWithValidDataReturnsCreated() {
        requireRunningApplication();
        String license = license();
        ApiResponse response = post(DOCTORS, doctor("Dr. Asha Menon", license, "Cardiology", "asha.menon@care.org", 12, 800.0));
        assertEquals(201, response.status, "POST /api/doctors with valid data must return 201");
        JsonNode body = parse(response.body);
        assertTrue(body.path("id").asLong(0) > 0, "Created doctor must contain a generated id");
        assertEquals(license, body.path("licenseNumber").asText(), "licenseNumber must match the request");
    }

    @Test
    void addDoctorWithDuplicateLicenseReturnsConflict() {
        requireRunningApplication();
        String license = license();
        createAndGetId(DOCTORS, doctor("Dr. Ravi Shankar", license, "Neurology", "ravi.s@care.org", 20, 1200.0));
        ApiResponse response = post(DOCTORS, doctor("Dr. Ravi Kumar", license, "Orthopedics", "ravi.k@care.org", 8, 600.0));
        assertEquals(409, response.status, "Adding a doctor with an existing license number must return 409");
        assertEquals("Doctor already exists with license number: " + license, parse(response.body).path("message").asText(),
                "409 response must contain the duplicate message");
    }

    @Test
    void conflictErrorResponseFollowsStandardFormat() {
        requireRunningApplication();
        String license = license();
        createAndGetId(DOCTORS, doctor("Dr. Neha Gupta", license, "Dermatology", "neha.g@care.org", 6, 500.0));
        ApiResponse response = post(DOCTORS, doctor("Dr. Neha Gupta", license, "Dermatology", "neha.g@care.org", 6, 500.0));
        assertEquals(409, response.status, "A duplicate doctor must return 409");
        assertStandardError(parse(response.body), 409, "Conflict", DOCTORS);
    }

    @Test
    void updateDoctorWithAnotherDoctorsLicenseReturnsConflict() {
        requireRunningApplication();
        String takenLicense = license();
        createAndGetId(DOCTORS, doctor("Dr. Farah Ali", takenLicense, "Pediatrics", "farah.ali@care.org", 10, 700.0));
        long id = createAndGetId(DOCTORS, doctor("Dr. John Mathew", license(), "ENT", "john.m@care.org", 15, 650.0));
        ApiResponse response = send("PUT", DOCTORS + "/" + id,
                doctor("Dr. John Mathew", takenLicense, "ENT", "john.m@care.org", 15, 650.0));
        assertEquals(409, response.status, "Updating a doctor to another doctor's license number must return 409");
    }

    @Test
    void updateDoctorKeepingOwnLicenseReturnsOk() {
        requireRunningApplication();
        String license = license();
        long id = createAndGetId(DOCTORS, doctor("Dr. Kiran Rao", license, "Oncology", "kiran.rao@care.org", 18, 1500.0));
        ApiResponse response = send("PUT", DOCTORS + "/" + id,
                doctor("Dr. Kiran Rao", license, "Oncology", "kiran.rao@care.org", 19, 1600.0));
        assertEquals(200, response.status, "Updating a doctor while keeping the same license number must return 200");
        assertEquals(19, parse(response.body).path("experienceYears").asInt(-1), "experienceYears must be updated");
    }

    @Test
    void addDoctorWithInvalidLicenseFormatReturnsBadRequest() {
        requireRunningApplication();
        ApiResponse response = post(DOCTORS, doctor("Dr. Leela Das", "LIC12345", "Gynecology", "leela.das@care.org", 9, 900.0));
        assertEquals(400, response.status, "A license number not matching MED followed by 5 digits must return 400");
        assertEquals("License number must start with MED followed by 5 digits", fieldError(response, "licenseNumber"),
                "fieldErrors.licenseNumber must contain the @Pattern message");
    }

    @Test
    void getDoctorsBySpecializationReturnsOnlyMatchingDoctors() {
        requireRunningApplication();
        String specialization = "Nephrology" + next();
        createAndGetId(DOCTORS, doctor("Dr. Arun Paul", license(), specialization, "arun.paul@care.org", 11, 750.0));
        createAndGetId(DOCTORS, doctor("Dr. Bina Shah", license(), specialization, "bina.shah@care.org", 7, 720.0));
        createAndGetId(DOCTORS, doctor("Dr. Carl Dsouza", license(), "Urology", "carl.d@care.org", 13, 820.0));
        ApiResponse response = get(DOCTORS + "/specialization/" + specialization);
        assertEquals(200, response.status, "GET /api/doctors/specialization/{specialization} must return 200");
        JsonNode body = parse(response.body);
        assertEquals(2, body.size(), "Exactly two doctors must be returned for the specialization");
        for (JsonNode node : body) {
            assertEquals(specialization, node.path("specialization").asText(),
                    "Every returned doctor must belong to the requested specialization");
        }
    }

    private static String license() {
        return "MED" + (10000 + next());
    }

    private static Map<String, Object> doctor(String name, String license, String specialization, String email,
            Integer experience, Double fee) {
        return json("doctorName", name, "licenseNumber", license, "specialization", specialization, "email", email,
                "experienceYears", experience, "consultationFee", fee);
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
