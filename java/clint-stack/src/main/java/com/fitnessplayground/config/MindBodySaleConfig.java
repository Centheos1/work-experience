package com.fitnessplayground.config;

import com.fitnessplayground.service.MindBodyServiceGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@PropertySource(value = "classpath:env.properties")
public class MindBodySaleConfig {

    @Value("${mindbody.service.sale.url}")
    private String MINDBODY_SALE_WEB_SERVICE_URI;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.fitnessplayground.model.mindbody.api.sale");
        return marshaller;
    }

    @Bean
    public MindBodyServiceGateway mindBodySale(Jaxb2Marshaller marshaller) {
        MindBodyServiceGateway client = new MindBodyServiceGateway();
        client.setDefaultUri(MINDBODY_SALE_WEB_SERVICE_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    /**
     * In order to resolve ${...} placeholders in <bean> definitions or @Value
     * annotations using properties from a PropertySource, one must register a
     * PropertySourcesPlaceholderConfigurer. This happens automatically when
     * using <context:property-placeholder> in XML, but must be explicitly
     * registered using a static @Bean method when using @Configuration classes.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
