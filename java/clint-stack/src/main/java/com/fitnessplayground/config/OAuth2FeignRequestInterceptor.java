package com.fitnessplayground.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;


public class OAuth2FeignRequestInterceptor implements RequestInterceptor{

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2FeignRequestInterceptor.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final OAuth2ClientContext clientContext;

    private final String token;
    private final boolean useToken;

    public OAuth2FeignRequestInterceptor( OAuth2ClientContext clientContext, boolean useToken, String token) {
        this.clientContext = clientContext;
        this.token = token;
        this.useToken = useToken;
    }

    @Override
    public void apply(RequestTemplate template) {

        if( useToken ) {
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, token));
            return;
        }

        if( template.headers().containsKey(AUTHORIZATION_HEADER)) {
            LOG.warn("Authorisation token has already been set");
        } else if( clientContext.getAccessTokenRequest().getExistingToken() == null) {
            LOG.warn("Can not obtain existing token for request");
        } else {
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, clientContext.getAccessTokenRequest().getExistingToken().toString()));
        }

    }
}
