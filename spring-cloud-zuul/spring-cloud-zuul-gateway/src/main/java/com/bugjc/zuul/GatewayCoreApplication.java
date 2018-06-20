package com.bugjc.zuul;

import com.bugjc.zuul.core.filter.AuthorizationRequestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class GatewayCoreApplication {

    @Bean
    public AuthorizationRequestFilter authorizationRequestFilter() {
        return new AuthorizationRequestFilter();
    }


    public static void main(String[] args) {
        SpringApplication.run(GatewayCoreApplication.class, args);
    }

}