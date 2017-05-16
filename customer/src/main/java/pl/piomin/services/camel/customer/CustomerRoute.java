package pl.piomin.services.camel.customer;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.consul.ConsulConfiguration;
import org.apache.camel.component.consul.cloud.ConsulServiceDiscovery;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.ribbon.RibbonConfiguration;
import org.apache.camel.component.ribbon.cloud.RibbonServiceLoadBalancer;
import org.apache.camel.model.cloud.ServiceCallConfigurationDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.config.DynamicIntProperty;

import pl.piomin.services.camel.common.model.Account;
import pl.piomin.services.camel.common.model.Customer;
import pl.piomin.services.camel.customer.service.AggregationStrategyImpl;

@Component
public class CustomerRoute extends RouteBuilder {
		
	@Autowired
	CamelContext context;
	
	@Value("${port}")
	private int port;
			
	@Override
	public void configure() throws Exception { 
		
		JacksonDataFormat format = new JacksonDataFormat();
		format.useList();
		format.setUnmarshalType(Account.class);
		
		ServiceCallConfigurationDefinition def = new ServiceCallConfigurationDefinition();
		
		ConsulConfiguration config = new ConsulConfiguration();
		config.setUrl("http://192.168.99.100:8500");
		ConsulServiceDiscovery discovery = new ConsulServiceDiscovery(config);
//		config.setComponent("netty4-http");
		
		RibbonConfiguration c = new RibbonConfiguration();
		RibbonServiceLoadBalancer lb = new RibbonServiceLoadBalancer(c);
		lb.setServiceDiscovery(discovery);
		
		def.setComponent("netty4-http");
		def.setLoadBalancer(lb);
		def.setServiceDiscovery(discovery);
//		context.setServiceCallConfiguration(def);
		
		restConfiguration()
			.component("netty4-http")
			.bindingMode(RestBindingMode.json)
			.port(port);
		
		from("direct:start").routeId("account-consul").marshal().json(JsonLibrary.Jackson)
			.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.to("http://192.168.99.100:8500/v1/agent/service/register");
		from("direct:stop").shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
			.toD("http://192.168.99.100:8500/v1/agent/service/deregister/${header.id}");
		
		rest("/customer")
			.get("/")
				.to("bean:customerService?method=findAll")
			.post("/").consumes("application/json").type(Customer.class)
				.to("bean:customerService?method=add(${body})")
			.get("/{id}").to("direct:account");
		
		
		from("direct:account")
			.to("bean:customerService?method=findById(${header.id})")
			.log("Msg: ${body}").enrich("direct:acc", new AggregationStrategyImpl());
		
		
		from("direct:acc").setBody().constant(null)
			.serviceCall()
				.component("netty4-http")
			 	.consulServiceDiscovery("http://192.168.99.100:8500")
			 	.ribbonLoadBalancer()
				.name("account//account");
//			.hystrix()
//				.hystrixConfiguration()
//					.executionTimeoutInMilliseconds(2000)
//				.end()
//				.setBody().constant(null).serviceCall("account//account").unmarshal(format)
//			.onFallback()
//				.to("bean:accountFallback?method=getAccounts")
//			.end();
			
	}
		
}
