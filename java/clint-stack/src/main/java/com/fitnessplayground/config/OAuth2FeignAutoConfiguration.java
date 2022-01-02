package com.fitnessplayground.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

@Configuration
public class OAuth2FeignAutoConfiguration {

    @Value("${security.oauth2.use-token:false}")
    private boolean useToken;

    @Value("${security.oauth2.token}")
    private String token;

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext clientContext) {
        return new OAuth2FeignRequestInterceptor(clientContext, useToken, token);
    }
}
