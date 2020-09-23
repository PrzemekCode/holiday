package com.interview.application.countries;

import com.interview.application.error_handler.ApiError;
import com.interview.application.exception.ApiException;
import com.interview.application.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class CountriesScheduler {

    private CountriesData countriesData;
    private RestTemplateService restTemplateService;
    private String urlCountries;

    @Autowired
    public CountriesScheduler(CountriesData countriesData, RestTemplateService restTemplateService, @Value("${HolidayApi.urlCountries}") String urlCountries) {
        this.countriesData = countriesData;
        this.restTemplateService = restTemplateService;
        this.urlCountries = urlCountries;
    }

    @Scheduled(cron = "0 1 1 * * ?")
    public void fetchCountries() {
        CountriesData countriesData  = restTemplateService.getForObject(urlCountries, CountriesData.class);
        this.countriesData.setCountries(ofNullable(countriesData)
                .map(CountriesData::getCountries)
                .orElseThrow(() -> new ApiException(ApiError.createInternalServerError("HolidayApi error"))));
    }
}
