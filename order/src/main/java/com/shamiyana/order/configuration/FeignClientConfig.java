package com.shamiyana.order.configuration;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FeignClientConfig {

    @Bean
    public Request.Options options(){
        return new Request.Options(   //It will take more priority than application.yml configuration
                5000,
                10000
        );
    }

    @Bean
    public Retryer getRetryer(){
        return new Retryer.Default();
    }
}
