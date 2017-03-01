package pl.piomin.services.camel.gateway;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RouteGateway extends RouteBuilder {
 
    @Override
    public void configure() throws Exception {
        from("direct:test").log("msg-${id}: ${body}").to("mock:test");
        
        from("direct:start").serviceCall("account-service").to("mock:result");
    }

}
