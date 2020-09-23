package com.interview.application.service;

import com.interview.application.countries.CountriesData;
import com.interview.application.model.Holiday;
import com.interview.application.model.HolidayApiResponse;
import com.interview.application.model.RequestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.interview.application.helper.DateHelper.DATE_FORMATTER;
import static com.interview.application.helper.ResponseDataHelper.COMMA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HolidayServiceTest {

    private HolidayService holidayService;
    private CountriesData countries;
    private RestTemplateService restTemplateService;

    private static final LocalDate NOW = LocalDate.now();
    private static final String NAME1 = "name1";
    private static final String NAME2 = "name2";
    private static final String NAME3 = "name3";
    private static final RequestData REQUEST_DATA = mock(RequestData.class);

    @BeforeEach
    void setUp() {
        countries = mock(CountriesData.class);
        restTemplateService = mock(RestTemplateService.class);
        String urlHolidays = "https://sampleurl.pl";
        holidayService = spy(new HolidayService(countries, restTemplateService, urlHolidays));
    }

    @Test
    void shouldReturnHolidayName1() {
        String countries = "pl,de";
        when(restTemplateService.getForObject(any(), any())).thenReturn(new HolidayApiResponse("200", generateHolidays(countries.split(COMMA))));
        List<Holiday> result = holidayService.findNextCommonHolidayInLanguageOfFirstCountry(RequestData.create("pl, de", "pl", NOW.plusWeeks(10).format(DATE_FORMATTER)));
        assertEquals(0, result.size());
    }

    @Test
    void shouldReturnEmptyResultIfDateAfterThanAllHolidays() {

        String countries = "pl,de";
        List<Holiday> generatedHolidays = generateHolidays(countries.split(COMMA));
        when(restTemplateService.getForObject(any(), any())).thenReturn(new HolidayApiResponse("200", generateHolidays(countries.split(COMMA))));
        List<Holiday> result = holidayService.findNextCommonHolidayInLanguageOfFirstCountry(RequestData.create("pl, de", "pl", "2020-02-02"));
        assertEquals(2, result.size());
        assertEquals(generatedHolidays.get(0).getName(), result.get(0).getName());
        assertEquals(generatedHolidays.get(0).getName(), result.get(1).getName());
    }

    private List<Holiday> generateHolidays(String[] countries) {
        List<Holiday> result = new ArrayList<>();
        Stream.of(countries).forEach(country -> result.addAll(
                Arrays.asList(Holiday.builder().date(NOW.minusWeeks(2)).name(NAME1).country(country).build(),
                        Holiday.builder().date(NOW.plusWeeks(2)).name(NAME2).country(country).build(),
                        Holiday.builder().date(NOW.plusWeeks(4)).name(NAME3).country(country).build())
        ));
        result.sort(Comparator.comparing(Holiday::getDate));
        return result;
    }

    @Test
    void shouldReplaceHolidayWithNameinLocalLanguag() {
        String country = "de";
        RequestData requestData = RequestData.create("pl," + country, country, "2020-02-20");
        Holiday holiday = mock(Holiday.class);
        Holiday holidayToReplace = Holiday.builder().country(country).date(NOW).build();
        List<Holiday> resultHoliday = new ArrayList<>(List.of(holiday, holidayToReplace));
        Holiday holidayWithProperLanguage = Holiday.builder().build();
        doReturn(holidayWithProperLanguage).when(holidayService).getHolidayByDate(any());

        holidayService.setHolidayOfSecondCountryInLocalLanguage(requestData, resultHoliday);
        assertTrue(resultHoliday.contains(holidayWithProperLanguage));
        assertFalse(resultHoliday.contains(holidayToReplace));
    }
}