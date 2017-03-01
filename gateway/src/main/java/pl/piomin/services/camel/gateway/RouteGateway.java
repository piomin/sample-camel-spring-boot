package pl.piomin.services.camel.gateway;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.remote.ConsulConfigurationDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteGateway extends RouteBuilder {
 
	@Autowired
	CamelContext context;
	
    @Override
    public void configure() throws Exception {
        from("direct:test").log("msg-${id}: ${body}").to("mock:test");
        
		ConsulConfigurationDefinition config = new ConsulConfigurationDefinition();
		config.setComponent("netty4-http");
		config.setUrl("http://192.168.99.100:8500");
		context.setServiceCallConfiguration(config);
		
        from("direct:start").serviceCall("account").log("msg-${id}: ${body}").to("mock:test");
    }

}
