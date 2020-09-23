package com.interview.application.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HolidayApiResponseTest {

    private final static LocalDate NOW = LocalDate.now();
    private static List<Holiday> list = List.of(Holiday.builder().country("PL").date(NOW.plusDays(1)).build(),
            Holiday.builder().country("DE").date(NOW.plusDays(2)).build(),
            Holiday.builder().country("PL").date(NOW.plusDays(3)).build(),
            Holiday.builder().country("DE").date(NOW.plusDays(3)).build(),
            Holiday.builder().country("PL").date(NOW.plusDays(4)).build());

    @Test
    void shouldReturnHolidaysAfterOrEqualDate() {
        HolidayApiResponse response = new HolidayApiResponse("status", list);
        List<Holiday> result = response.getHolidaysAfterDate(NOW.plusDays(3));
        assertEquals(3, result.size());
    }

    @Test
    void shouldReturnNextCommonHolidayAfterOrEqualDate() {
        HolidayApiResponse response = new HolidayApiResponse("status", list);
        List<Holiday> result = response.findNextCommonHolidayAfterDate(NOW);
        assertEquals(NOW.plusDays(3), result.get(0).getDate());
        assertEquals("PL", result.get(0).getCountry());
        assertEquals("DE", result.get(1).getCountry());
    }
}