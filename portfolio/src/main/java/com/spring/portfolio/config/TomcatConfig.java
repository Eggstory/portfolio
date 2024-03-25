package com.spring.portfolio.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setDocumentRoot(new File("C:\\Users\\khm99\\OneDrive\\바탕 화면\\namoo\\git_repo\\portfolio\\src\\main\\resources\\static"));
    }
}
