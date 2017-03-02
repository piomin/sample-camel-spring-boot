package pl.piomin.services.camel.account;

import javax.annotation.PostConstruct;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pl.piomin.services.camel.account.model.Account;

@Component
public class AccountRoute extends RouteBuilder {
	
	@Value("${port}")
	private int port;
		
	@Override
	public void configure() throws Exception {  
		restConfiguration()
			.component("netty4-http")
			.bindingMode(RestBindingMode.json)
			.port(port);
		
		from("timer://runOnce?repeatCount=1&delay=5000").to("bean:registration?method=register");
		from("direct:start").marshal().json(JsonLibrary.Jackson)
			.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.to("http://192.168.99.100:8500/v1/agent/service/register");
		
		rest("/")
			.get("/{id}")
				.to("bean:accountService?method=findById(${header.id})")
			.get("/customer/{customerId}")
				.to("bean:accountService?method=findByCustomerId(${header.customerId})")
			.get("/")
				.to("bean:accountService?method=findAll")
			.post("/").consumes("application/json").type(Account.class)
				.to("bean:accountService?method=add(${body})");
		
	}

@PostConstruct
public void info() {
	System.out.println("fe");
}
}
