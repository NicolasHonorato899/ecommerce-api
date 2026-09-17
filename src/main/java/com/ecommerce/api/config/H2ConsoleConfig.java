package com.ecommerce.api.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot 4 removed H2ConsoleAutoConfiguration (the spring.h2.console.* properties no longer bind),
 * so the H2 web console servlet is registered manually here. Keep it enabled only in non-prod profiles:
 * see application-h2.properties for the profile config.
 */
@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServletRegistration() {
        ServletRegistrationBean<JakartaWebServlet> registration =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        registration.setLoadOnStartup(1);
        return registration;
    }
}