package pl.piomin.services.camel.customer;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CamelOpenTelemetry
public class CamelCustomerService {

    public static void main(String[] args) {
        SpringApplication.run(CamelCustomerService.class, args);
    }

}
