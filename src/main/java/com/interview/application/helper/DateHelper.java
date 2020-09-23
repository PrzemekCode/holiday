package com.interview.application.helper;

import com.interview.application.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.format.ResolverStyle.STRICT;

public class DateHelper {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(STRICT);

    public static String localDateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static LocalDate stringToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ValidationException("Wrong format of date: " + date);
        }
    }
}
