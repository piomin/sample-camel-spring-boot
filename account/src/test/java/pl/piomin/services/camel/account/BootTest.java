package pl.piomin.services.camel.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"port=4040"})
public class BootTest {
    
	@Test
	public void test1() throws InterruptedException {
		Thread.sleep(3000);
	}
	
}
