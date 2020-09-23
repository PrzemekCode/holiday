package com.interview.application.helper;

import com.interview.application.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.interview.application.helper.DateHelper.DATE_FORMATTER;
import static com.interview.application.helper.DateHelper.stringToLocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {

    @Test
    void shouldParseStringAccordingToDateFormatter() {
        String date = "2020-10-10";
        LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
        assertEquals(localDate, stringToLocalDate(date));
    }

    @Test
    void shouldThrowExceptionIfWrongFormatOfDate() {
        String date = "2020-10.10";
        assertThrows(ValidationException.class, () -> stringToLocalDate(date));
    }
}