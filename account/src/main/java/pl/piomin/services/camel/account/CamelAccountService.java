package pl.piomin.services.camel.account;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CamelOpenTelemetry
public class CamelAccountService {

    public static void main(String[] args) {
        SpringApplication.run(CamelAccountService.class, args);
    }

}
