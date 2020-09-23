package com.interview.application.helper;

import com.interview.application.exception.ValidationException;
import com.interview.application.model.RequestData;

import java.util.Objects;

import static com.interview.application.helper.ResponseDataHelper.COMMA;

public class ValidationHelper {

    public static void validateSearchData(RequestData data) {
        validateQueryParameters(data);
        validateCountries(data.getCountries());
    }

    private static void validateQueryParameters(RequestData data) {
        if(Objects.isNull(data.getDate())) {
            throw new ValidationException("Parameter date is mandatory");
        } else if(Objects.isNull(data.getCountries())) {
            throw new ValidationException("Parameter countries is mandatory");
        }
    }

    private static void validateCountries(String countries) {
        if(countries.isBlank() || !countries.contains(COMMA) || countries.split(COMMA).length != 2 || isAnyCodeEmpty(countries)) {
            throw new ValidationException("Wrong data about countries: " + countries);
        }
        isDuplicationCounries(countries);
    }

    private static void isDuplicationCounries(String countries) {
        String[] splittedCountries = countries.split(COMMA);
        if(splittedCountries[0].trim().equalsIgnoreCase(splittedCountries[1].trim())) {
            throw new ValidationException("The same country is duplicated: " + countries);
        }
    }

    private static boolean isAnyCodeEmpty(String countries) {
        String[] codes = countries.split(COMMA);
        return codes[0].isBlank() || codes[1].isBlank();
    }
}
