package com.interview.application.helper;

import com.interview.application.model.Holiday;

import java.util.List;
import java.util.Objects;

public class ResponseDataHelper {

    public final static String COMMA = ",";

    public static Holiday getHolidayByCountryCode(String countryCode, List<Holiday> holidays) {
        return holidays.stream()
                .filter(holiday -> Objects.nonNull(countryCode) && countryCode.equalsIgnoreCase(holiday.getCountry()))
                .findFirst().orElseThrow();
    }
}
