package pl.piomin.services.camel.gateway;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GatewayTest {

	@Autowired
	ProducerTemplate template;
	
    @EndpointInject(uri = "mock:test")
    MockEndpoint mockEndpoint;
    
//	@Test
//	@Ignore
	public void test1() throws InterruptedException {
		System.out.println("Test");
		Thread.sleep(3000);
		mockEndpoint.setExpectedMessageCount(1);
		template.sendBody("direct:start", "This is a test message");
		mockEndpoint.assertIsSatisfied();
	}
	
}
