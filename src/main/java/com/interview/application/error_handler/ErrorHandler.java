package com.interview.application.error_handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.application.exception.ApiException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Objects;

public class ErrorHandler extends DefaultResponseErrorHandler {

    private static final String PAYMENT_REQUIRED_CODE = "Payment Required";
    private static final String UNSUPPORTED_LANGUAGE_MESSAGE = "The requested language is not supported at this time";

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            ApiError apiError = new ObjectMapper().readValue(response.getBody(), ApiError.class);
            customizeStatusses(apiError, response.getStatusCode().getReasonPhrase());
            throw new ApiException(apiError);
        }
    }

    private void customizeStatusses(ApiError apiError, String status) {
        String error = apiError.getError();
        if(PAYMENT_REQUIRED_CODE.equals(status)) {
            apiError.setError("You are limited to last year's historical data only");
        } else if(Objects.nonNull(error) && error.startsWith(UNSUPPORTED_LANGUAGE_MESSAGE)) {
            apiError.setError(UNSUPPORTED_LANGUAGE_MESSAGE);
        }
    }
}
