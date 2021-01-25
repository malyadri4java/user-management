package com.dailycode.user.management.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

    public static String dateAndTimeAsString() {
        return LocalDateTime.now().format(formatter);
    }

    public static String dateAndTimeAsString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }


}
