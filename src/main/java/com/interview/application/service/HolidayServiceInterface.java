package com.interview.application.service;

public interface HolidayServiceInterface<T, E> {
    T getNextCommonHoliday(E data);
}
