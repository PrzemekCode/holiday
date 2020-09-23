package com.interview.application.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.*;

@Component
public class HttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        res.addHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET");
        res.addHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        res.addHeader("X-Frame-Options", "Deny");
        chain.doFilter(req, res);
    }
}
