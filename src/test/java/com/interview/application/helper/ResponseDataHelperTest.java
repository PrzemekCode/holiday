package com.interview.application.helper;

import com.interview.application.model.Holiday;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.interview.application.helper.ResponseDataHelper.getHolidayByCountryCode;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseDataHelperTest {

    @Test
    void shouldFindCountryWithIgnoreCase() {
        String countryCode = "pl";
        List<Holiday> list = List.of(Holiday.builder().country("fr").build(), Holiday.builder().country("by").build(), Holiday.builder().country(countryCode.toUpperCase()).build());
        Holiday result = getHolidayByCountryCode(countryCode, list);
        assertTrue(countryCode.equalsIgnoreCase(result.getCountry()));
    }
}