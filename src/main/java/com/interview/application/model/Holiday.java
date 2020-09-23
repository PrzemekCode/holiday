package com.interview.application.model;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    private String name;
    private LocalDate date;
    private String country;
}
