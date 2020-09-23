package com.interview.application.config;

import com.interview.application.error_handler.ErrorHandler;
import com.interview.application.interceptor.HttpInterceptor;
import com.interview.application.countries.CountriesData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Configuration
public class ApplicationConfig {

    @Value("${HolidayApi.key}")
    private String key;

    @Value("${HolidayApi.urlCountries}")
    private String urlCountries;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ErrorHandler());
        List<ClientHttpRequestInterceptor> interceptors = ofNullable(restTemplate.getInterceptors()).orElse(new ArrayList<>());
        interceptors.add(new HttpInterceptor(key));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    @EventListener(ApplicationReadyEvent.class)
    public CountriesData countries() {
        return new CountriesData(getCountries().getCountries());
    }

    private CountriesData getCountries(){
        RestTemplate rest = restTemplate();
        return rest.getForObject(urlCountries, CountriesData.class);
    }
}
