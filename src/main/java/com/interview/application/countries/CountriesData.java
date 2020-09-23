package com.interview.application.countries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interview.application.exception.ValidationException;
import com.interview.application.model.Country;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Builder
public class CountriesData {

    private List<Country> countries;

    @JsonCreator
    public CountriesData(@JsonProperty("countries") List<Country> countries) {
        this.countries = countries;
    }

    public Country getCountryByCountryCode(String countryCode) {
        return countries.stream()
                .filter(country -> Objects.nonNull(country.getCode()) && country.getCode().equalsIgnoreCase(countryCode))
                .findFirst().orElseThrow(() -> new ValidationException("Unsupported Country: " + countryCode));
    }

    public String getLanguageByCountryCode(String countryCode) {
        return getCountryByCountryCode(countryCode).getLanguage();
    }
}
