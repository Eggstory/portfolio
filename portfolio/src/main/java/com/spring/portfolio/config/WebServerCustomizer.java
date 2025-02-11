package com.spring.portfolio.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage4xx = new ErrorPage(HttpStatus.NOT_FOUND, "/error/4xx");
        ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
        ErrorPage errorPage403 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/403");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");

        ErrorPage runtimeErrorPage = new ErrorPage(RuntimeException.class, "/error/500");

        factory.addErrorPages(errorPage4xx, errorPage403, errorPage400, errorPage500, runtimeErrorPage);
    }
}
