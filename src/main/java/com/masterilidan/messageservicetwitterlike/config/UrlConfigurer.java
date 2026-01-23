package com.masterilidan.messageservicetwitterlike.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlConfigurer {
    @Value("${url.baseUrl.UserService}")
    private String baseAddrUserService;

    @Value("${url.baseUrl.MessageService}")
    private String baseAddrMessageService;

    @Value("${url.baseUrl.SubsService}")
    private String baseAddrSubsService;

    @Bean
    public String getBaseAddrMessageService() {
        return baseAddrMessageService;
    }

    @Bean
    public String getBaseAddrSubsService() {
        return baseAddrSubsService;
    }

    @Bean
    public String getBaseAddrUserService() {
        return baseAddrUserService;
    }
}
