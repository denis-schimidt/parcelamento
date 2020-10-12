package com.viavarejo.vendas.parcelamento.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

import java.time.Duration;

@Configuration
class EndpointExternoConfig {

    @Bean(name = "endpointPesquisaTaxaSelic")
    String getEndpointPesquisaTaxaSelic( @Value("${selic.endpoint.uri}") String endpointPesquisaTaxaSelic,
                                         @Value("${selic.endpoint.dias-atras-pesquisa}") int diasAtrasPesquisa) {
        return String.format(endpointPesquisaTaxaSelic, diasAtrasPesquisa);
    }

    @Bean
    RestOperations newRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                   @Value("${selic.endpoint.connection-timeout}") int connectionTimeout,
                                   @Value("${selic.endpoint.read-timeout}") int readTimeout) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
