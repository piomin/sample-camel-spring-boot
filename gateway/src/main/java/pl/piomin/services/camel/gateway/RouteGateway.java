package pl.piomin.services.camel.gateway;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.remote.ConsulConfigurationDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteGateway extends RouteBuilder {
 
	@Autowired
	CamelContext context;
	
    @Override
    public void configure() throws Exception {
        
		ConsulConfigurationDefinition config = new ConsulConfigurationDefinition();
		config.setComponent("netty4-http");
		config.setUrl("http://192.168.99.100:8500");
		context.setServiceCallConfiguration(config);
		
        from("direct:start").serviceCall("account//acc/all").to("mock:test");
        
		restConfiguration()
		.component("netty4-http")
		.bindingMode(RestBindingMode.json)
		.port(8000);
		
		from("rest:get:account?produces=application/json").serviceCall("account//acc/all");
    }

}
