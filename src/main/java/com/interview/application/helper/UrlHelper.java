package com.interview.application.helper;

import com.interview.application.model.RequestData;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

public class UrlHelper {

    public static String prepareUrlFetchHolidays(RequestData data, String baseUrl) {
        return getBaseUriComponentsBuilder(data, baseUrl)
                .build()
                .toUriString();
    }

    public static String prepareUrlFetchHoliday(RequestData data, String baseUrl) {
        LocalDate date = data.getDateAsLocalDate();
        return getBaseUriComponentsBuilder(data, baseUrl)
                .queryParam("month", date.getMonth().getValue())
                .queryParam("day", date.getDayOfMonth())
                .build()
                .toUriString();
    }

    private static UriComponentsBuilder getBaseUriComponentsBuilder(RequestData data, String baseUrl) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("pretty", true)
                .queryParam("country", data.getCountries())
                .queryParam("language", data.getLanguage())
                .queryParam("year", data.getDateAsLocalDate().getYear());
    }
}
