package pl.piomin.services.camel.customer;

import org.junit.jupiter.api.Test;
import org.kiwiproject.consul.Consul;
import org.kiwiproject.consul.model.health.Service;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.consul.ConsulContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = "camel.netty-http.port=8082")
@Testcontainers
@DirtiesContext
public class CustomerConsulTests {

    @Container
    static ConsulContainer consul = new ConsulContainer("hashicorp/consul:1.20");

    @DynamicPropertySource
    static void consulProperties(DynamicPropertyRegistry registry) {
        registry.add("camel.component.consul.url",
                () -> "http://" + consul.getHost() + ":" + consul.getMappedPort(8500));
    }

    @Test
    public void shouldRegisterServiceInConsul() {
        Consul client = Consul.builder()
                .withUrl("http://" + consul.getHost() + ":" + consul.getMappedPort(8500))
                .build();

        await().atMost(ofSeconds(15)).until(() -> {
            Map<String, Service> services = client.agentClient().getServices();
            return services.containsKey("customer-service");
        });

        Map<String, Service> services = client.agentClient().getServices();
        Service service = services.get("customer-service");
        assertNotNull(service);
        assertEquals("customer", service.getService());
        assertEquals(8082, service.getPort());
    }

}