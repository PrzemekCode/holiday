package com.interview.application.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Country {
    private String code;
    private List<String> lnguages;

    @JsonCreator
    public Country(@JsonProperty("code") String code,@JsonProperty("languages") List<String> lnguages) {
        this.code = code;
        this.lnguages = lnguages;
    }

    public String getLanguage() {
        return lnguages.get(0);
    }
}
