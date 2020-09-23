package com.interview.application.exception;

import com.interview.application.error_handler.ApiError;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ApiError apiError;

    public ApiException(ApiError apiError) {
        super();
        this.apiError = apiError;
    }
}
