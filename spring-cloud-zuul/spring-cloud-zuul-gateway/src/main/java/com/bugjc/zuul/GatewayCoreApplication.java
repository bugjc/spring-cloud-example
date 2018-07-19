package com.bugjc.zuul;

import com.bugjc.zuul.core.filter.AuthorizationRequestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @RestController
    public class ServiceController {

        public static final String RESPONSE_BODY = "ResponseBody";

        @GetMapping("/serviceA")
        public ResponseEntity<String> serviceA() {
            return ResponseEntity.ok(RESPONSE_BODY);
        }
    }

}