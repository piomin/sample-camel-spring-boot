package pl.piomin.services.camel.account;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.consul.ConsulConstants;
import org.apache.camel.component.consul.enpoint.ConsulKeyValueActions;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.piomin.services.camel.account.model.Account;

@Component
public class AccountRoute extends RouteBuilder {

	@Autowired
	CamelContext context;
	
	@Override
	public void configure() throws Exception {
		
	       from("direct:put")
           .to("consul:kv-put")
               .to("log:camel-consul?level=INFO");
	       
		restConfiguration()
			.component("netty4-http")
			.bindingMode(RestBindingMode.json)
			.contextPath("/")
			.port(4040)
			.apiContextPath("api-doc").apiProperty("api.title", "Account API");

		rest("/account")
			.get("/{id}")
			.description("Find Account by id").outType(Account.class)
			.param().name("id").type(RestParamType.path).description("User identificator").dataType("int").endParam()
			.to("bean:accountService?method=findById(${header.id})");
		
//		from("").loadBalance().roundRobin();
	}

}
