package com.knits.enterprise.config;

import java.time.format.DateTimeFormatter;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final Integer DEFAULT_JPA_PAGE_SIZE = 50;
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String NO_CREDENTIALS = null;
    private Constants() {}


    public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String TIME_FORMAT_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT_DD_MM_YYYY_HH_MM_SS);
}
