package com.interview.application.service;

import com.interview.application.error_handler.ApiError;
import com.interview.application.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestTemplateService {

    private final RestTemplate restTemplate;

    public <T> T getForObject(String url, Class<T> responseType) {
        T entity = restTemplate.getForObject(url, responseType);
        if(Objects.isNull(entity)) {
            throw new ApiException(ApiError.createInternalServerError("HolidayApi error"));
        }
        return entity;
    }

}
