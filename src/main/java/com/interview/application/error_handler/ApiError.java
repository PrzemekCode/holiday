package com.interview.application.error_handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ApiError {
    private String status;
    private String error;
    @JsonIgnore
    private Object requests;

    public static ApiError createApiError(String status, String error) {
        return ApiError.builder()
                .error(error)
                .status(status)
                .build();
    }

    public static ApiError createInternalServerError(String error) {
        return ApiError.builder()
                .status(INTERNAL_SERVER_ERROR.toString())
                .error(error)
                .build();
    }

    public static ApiError createValidationError(String error) {
        return ApiError.builder()
                .status(UNPROCESSABLE_ENTITY.toString())
                .error(error)
                .build();
    }
}
