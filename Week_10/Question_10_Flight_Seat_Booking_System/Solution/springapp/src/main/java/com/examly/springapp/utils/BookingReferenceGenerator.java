package com.examly.springapp.utils;

import java.util.UUID;

public final class BookingReferenceGenerator {

    private static final String PREFIX = "BK";

    private BookingReferenceGenerator() {
    }

    public static String generate() {
        String random = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return PREFIX + random;
    }
}
