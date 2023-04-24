package pl.piomin.services.camel.customer;

import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.piomin.services.camel.common.model.Customer;

@SpringBootTest
//@ExtendWith(HoverflyExtension.class)
public class CustomerRouteTests {

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void add() {
        Customer c = Instancio.create(Customer.class);
        c = restTemplate.postForObject("http://localhost:8080/customer", c, Customer.class);
        Assertions.assertNotNull(c);
        Assertions.assertEquals(1, c.getId());
    }

    @Test
    public void findAll() {
        Customer[] customers = restTemplate.getForObject("http://localhost:8080/customer", Customer[].class);
        Assertions.assertTrue(customers.length > 0);
    }

//	@Test
//	public void findById(Hoverfly hoverfly) {
//        hoverfly.simulate(
//                dsl(service("http://account:8080")
//                        .get("/")
//                        .willReturn(success("[{\"id\":\"1\",\"number\":\"1234567890\"}]", "application/json"))
//        ));
//		Customer a = restTemplate.getForObject("http://localhost:8080/customer/1", Customer.class);
//		Assertions.assertNotNull(a);
//		Assertions.assertEquals(1, a.getId());
//	}

}
