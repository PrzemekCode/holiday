package com.interview.application.helper;

import com.interview.application.exception.ValidationException;
import com.interview.application.model.RequestData;
import org.junit.jupiter.api.Test;

import static com.interview.application.helper.ValidationHelper.validateSearchData;
import static org.junit.jupiter.api.Assertions.*;

class ValidationHelperTest {

    private final static RequestData.RequestDataBuilder BUILDER = RequestData.builder();

    @Test
    void shouldThrowExceptionIfOnlyOneCountryIsGiven() {
        assertThrows(ValidationException.class, () -> validateSearchData(BUILDER.countries("pl").build()));
    }

    @Test
    void shouldThrowExceptionIfCountriesAreBlankString() {
        assertThrows(ValidationException.class, () -> validateSearchData(BUILDER.countries("").build()));
    }

    @Test
    void shouldThrowExceptionIfCountriesAreNotSeparatedByComma() {
        assertThrows(ValidationException.class, () -> validateSearchData(BUILDER.countries("ples").build()));
    }

    @Test
    void shouldThrowExceptionIfMoreThanTwoCountries() {
        assertThrows(ValidationException.class, () -> validateSearchData(BUILDER.countries("pl,es,fr").build()));
    }

    @Test
    void shouldThrowExceptionIfOneCountryIsBlank() {
        assertThrows(ValidationException.class, () -> validateSearchData(BUILDER.countries("pl,").build()));
    }

    @Test
    void shouldThrowExceptionIfBothCountriesAreBlank() {
        assertThrows(ValidationException.class, () -> validateSearchData(BUILDER.countries(",").build()));
    }
}