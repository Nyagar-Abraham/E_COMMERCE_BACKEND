package org.abraham.apigateway;

import org.abraham.apigateway.datafetchers.AuthDataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class ApigatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(ApigatewayApplication.class);

    public static void main(String[] args) {
      var context =   SpringApplication.run(ApigatewayApplication.class, args);
        AuthDataFetcher authDataFetcher = context.getBean(AuthDataFetcher.class);
        authDataFetcher
                .registerUser()
                .publishOn(Schedulers.boundedElastic())
                .subscribe(
                        user -> {log.info("New User , {}",user);},
                        err -> log.error("error: {}",err.getMessage())
                );
    }



}
