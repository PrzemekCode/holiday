package com.interview.application.service;

import com.interview.application.countries.CountriesData;
import com.interview.application.model.Holiday;
import com.interview.application.model.HolidayApiResponse;
import com.interview.application.model.RequestData;
import com.interview.application.model.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.interview.application.helper.ResponseDataHelper.getHolidayByCountryCode;
import static com.interview.application.helper.UrlHelper.prepareUrlFetchHoliday;
import static com.interview.application.helper.UrlHelper.prepareUrlFetchHolidays;
import static com.interview.application.helper.ValidationHelper.validateSearchData;

@Service
public class HolidayService implements HolidayServiceInterface<ResponseData, RequestData> {

    private CountriesData countries;
    private RestTemplateService restTemplateService;
    private String urlHolidays;

    @Autowired
    public HolidayService(CountriesData countries, RestTemplateService restTemplateService, @Value("${HolidayApi.urlHolidays}") String urlHolidays) {
        this.countries = countries;
        this.restTemplateService = restTemplateService;
        this.urlHolidays = urlHolidays;
    }

    public ResponseData getNextCommonHoliday(RequestData data) {
        validateSearchData(data);
        data.trimParameters();
        data.setLanguage(countries, data.getFirstCountry());
        List<Holiday> resultHoliday = findNextCommonHolidayInLanguageOfFirstCountry(data);
        setHolidayOfSecondCountryInLocalLanguage(data, resultHoliday);
        return ResponseData.create(resultHoliday);
    }

    /**
     *
     * all holiday's names of both countries are in language of first country. It's limitation of HolidayApi, you can choose one language
     * @param data
     * @return
     */
    List<Holiday> findNextCommonHolidayInLanguageOfFirstCountry(RequestData data) {
        HolidayApiResponse responseWithAllHolidaysInFirstLanguage = restTemplateService.getForObject(prepareUrlFetchHolidays(data, urlHolidays), HolidayApiResponse.class);
        return responseWithAllHolidaysInFirstLanguage.findNextCommonHolidayAfterDate(data.getDateAsLocalDate());
    }

    void setHolidayOfSecondCountryInLocalLanguage(RequestData data, List<Holiday> resultHoliday) {
        Holiday holidaySecondCountry = getHolidayByCountryCode(data.getSecondCountry(), resultHoliday);
        Holiday holidaySecondCountryLocalLanguage = getHolidayByDate(RequestData.create(holidaySecondCountry, countries));
        resultHoliday.set(resultHoliday.indexOf(holidaySecondCountry), holidaySecondCountryLocalLanguage);
    }

    Holiday getHolidayByDate(RequestData data) {
        HolidayApiResponse response = restTemplateService.getForObject(prepareUrlFetchHoliday(data, urlHolidays), HolidayApiResponse.class);
        return response.getHolidays().get(0);
    }
}
