package com.interview.application.controller;

import com.interview.application.error_handler.ApiError;
import com.interview.application.exception.ApiException;
import com.interview.application.exception.ValidationException;
import com.interview.application.model.RequestData;
import com.interview.application.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("next-holiday")
    public ResponseEntity<Object> getNextHoliday(RequestData data) {
        try {
            return ResponseEntity.ok(holidayService.getNextCommonHoliday(data));
        } catch (ApiException ex) {
            return ResponseEntity.badRequest().body(ex.getApiError());
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(ApiError.createValidationError(ex.getMessage()));
        }
    }

}
