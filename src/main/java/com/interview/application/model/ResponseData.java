package com.interview.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.interview.application.helper.DateHelper.localDateToString;

@Builder
@AllArgsConstructor
@Getter
public class ResponseData {
    private String date;
    private String name1;
    private String name2;

    public static ResponseData create(List<Holiday> holidays) {
        Holiday firstHoliday = holidays.get(0);
        return ResponseData.builder()
                .date(localDateToString(firstHoliday.getDate()))
                .name1(firstHoliday.getName())
                .name2(holidays.get(1).getName())
                .build();
    }
}
