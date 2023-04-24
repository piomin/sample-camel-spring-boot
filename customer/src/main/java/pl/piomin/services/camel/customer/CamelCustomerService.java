package pl.piomin.services.camel.customer;

import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CamelZipkin
public class CamelCustomerService {

    public static void main(String[] args) {
        SpringApplication.run(CamelCustomerService.class, args);
    }

}
