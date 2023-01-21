package pl.piomin.services.camel.account;

import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.piomin.services.camel.common.model.Account;

@SpringBootTest
public class AccountRouteTests {

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void add() {
		Account a = Instancio.create(Account.class);
		a = restTemplate.postForObject("http://localhost:8080/account", a, Account.class);
		Assertions.assertNotNull(a);
		Assertions.assertEquals(1, a.getId());
	}

	@Test
	public void findAll() {
		Account[] accounts = restTemplate.getForObject("http://localhost:8080/account/", Account[].class);
		Assertions.assertTrue(accounts.length > 0);
	}

	@Test
	public void findById() {
		Account a = restTemplate.getForObject("http://localhost:8080/account/1", Account.class);
		Assertions.assertNotNull(a);
		Assertions.assertEquals(1, a.getId());
	}

	@Test
	public void findByCustomerId() {
		Account[] accounts = restTemplate.getForObject("http://localhost:8080/account/customer/{customerId}", Account[].class, 1);
		Assertions.assertTrue(accounts.length > 0);
	}

}
