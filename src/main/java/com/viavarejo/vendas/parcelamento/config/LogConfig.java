package com.viavarejo.vendas.parcelamento.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
class LogConfig {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    Logger logger(InjectionPoint ip) {
        Class<?> targetClass = ip.getMethodParameter() != null ? ip.getMethodParameter().getContainingClass() : ip.getField().getDeclaringClass();

        return LoggerFactory.getLogger(targetClass);
    }
}
