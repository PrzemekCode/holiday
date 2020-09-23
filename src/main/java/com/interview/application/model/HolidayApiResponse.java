package com.interview.application.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Getter
public class HolidayApiResponse {
    private String status;
    private List<Holiday> holidays;

    @JsonCreator
    public HolidayApiResponse(@JsonProperty("status") String status, @JsonProperty("holidays") List<Holiday> holidays) {
        this.status = status;
        this.holidays = holidays;
    }

    public List<Holiday> getHolidaysAfterDate(LocalDate localDate) {
        return holidays.stream()
                .filter(data -> !data.getDate().isBefore(localDate))
                .collect(Collectors.toList());
    }

    public List<Holiday> findNextCommonHolidayAfterDate(LocalDate date) {
        return findFirstCommonHoliday(getHolidaysAfterDate(date));
    }

    private List<Holiday> findFirstCommonHoliday(List<Holiday> data) {
        for (int i = 0; i < data.size() - 1; i++) {
            Holiday currentIteration = data.get(i);
            Holiday nextIteration = data.get(i + 1);
            if (currentIteration.getDate().isEqual(nextIteration.getDate()) &&
                    !currentIteration.getCountry().equals(nextIteration.getCountry())) {
                return new ArrayList<>(Arrays.asList(currentIteration, nextIteration));
            }
        }
        return emptyList();
    }
}
