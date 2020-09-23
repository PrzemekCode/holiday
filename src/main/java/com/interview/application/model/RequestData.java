package com.interview.application.model;

import com.interview.application.countries.CountriesData;
import lombok.*;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.interview.application.helper.DateHelper.localDateToString;
import static com.interview.application.helper.ResponseDataHelper.COMMA;
import static com.interview.application.helper.DateHelper.stringToLocalDate;
import static java.util.Optional.ofNullable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {
    private String countries;
    private String date;
    private String language;

    public LocalDate getDateAsLocalDate() {
        return stringToLocalDate(date);
    }

    public static RequestData create(String countries, String language, String date) {
        return RequestData.builder()
                .countries(trimParameter(countries, COMMA))
                .language(language)
                .date(date)
                .build();
    }

    public void trimParameters() {
        setCountries(ofNullable(countries).map(countries -> trimParameter(countries, COMMA)).orElse(null));
        setDate(ofNullable(date).map(RequestData::trimParameter).orElse(null));
        setLanguage(ofNullable(language).map(RequestData::trimParameter).orElse(null));
    }

    private static String trimParameter(String parameter) {
        return trimParameter(parameter, null);
    }

    private static String trimParameter(String parameter, String delimeter) {
        return ofNullable(delimeter).map(del -> trimParameterWithDelimeter(parameter, del)).orElse(parameter.trim());
    }

    private static String trimParameterWithDelimeter(String parameter, String delimeter) {
        return Stream.of(parameter.split(delimeter))
                .map(String::trim)
                .collect(Collectors.joining(delimeter));
    }

    public static RequestData create(Holiday holiday, CountriesData countries) {
        String countryCode = holiday.getCountry();
        return RequestData.create(countryCode, countries.getLanguageByCountryCode(countryCode), localDateToString(holiday.getDate()));
    }

    public String getFirstCountry() {
        return countries.split(COMMA)[0].trim();
    }

    public String getSecondCountry() {
        return countries.split(COMMA)[1].trim();
    }

    public void setLanguage(CountriesData countriesData, String countryCode) {
        Country firstCountry = countriesData.getCountryByCountryCode(countryCode);
        setLanguage(firstCountry.getLanguage());
    }
}
