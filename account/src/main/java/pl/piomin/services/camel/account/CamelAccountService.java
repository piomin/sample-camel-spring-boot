package pl.piomin.services.camel.account;

import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@CamelZipkin
public class CamelAccountService {

    public static void main(String[] args) {
        SpringApplication.run(CamelAccountService.class, args);
    }

}
