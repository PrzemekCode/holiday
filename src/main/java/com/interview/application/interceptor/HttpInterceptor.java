package com.interview.application.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
public class HttpInterceptor
        implements ClientHttpRequestInterceptor {

    private final String key;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(addKeyParameter(request), body);
    }

    private HttpRequest addKeyParameter(HttpRequest request) {
        URI uriWithKey = UriComponentsBuilder.fromUri(request.getURI()).queryParam("key", key).build().toUri();
        return new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uriWithKey;
            }

        };
    }
}
