package pl.piomin.services.camel.customer;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.kiwiproject.consul.model.agent.ImmutableRegistration;
import org.kiwiproject.consul.model.agent.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.piomin.services.camel.common.model.Account;
import pl.piomin.services.camel.common.model.Customer;
import pl.piomin.services.camel.customer.service.AggregationStrategyImpl;

@Component
public class CustomerRoute extends RouteBuilder {

    @Value("${camel.netty-http.port:8080}")
    private int port;

    @Autowired
    CamelContext context;

    @Override
    public void configure() throws Exception {

        JacksonDataFormat format = new JacksonDataFormat();
        format.useList();
        format.setUnmarshalType(Account.class);

        restConfiguration()
                .component("netty-http")
                .bindingMode(RestBindingMode.json)
                .port(port);

        from("timer:consul-reg?repeatCount=1&delay=2000")
                .routeId("customer-consul-register")
                .process(exchange -> {
                    Registration reg = ImmutableRegistration.builder()
                            .id("customer-service")
                            .name("customer")
                            .address("localhost")
                            .port(port)
                            .build();
                    exchange.getIn().setBody(reg);
                })
                .doTry()
                    .to("consul:agent?action=REGISTER")
                .doCatch(Exception.class)
                    .log("Failed to register with Consul: ${exception.message}")
                .end();

        from("direct:consul-deregister")
                .routeId("customer-consul-deregister")
                .setBody(constant("customer-service"))
                .doTry()
                    .to("consul:agent?action=DEREGISTER")
                .doCatch(Exception.class)
                    .log("Failed to deregister from Consul: ${exception.message}")
                .end();

        rest("/customer")
            .get("/")
                .to("bean:customerService?method=findAll")
            .post("/").consumes("application/json").type(Customer.class)
                .to("bean:customerService?method=add(${body})")
            .get("/{id}").to("direct:account").produces("application/json");


        from("direct:account")
                .to("bean:customerService?method=findById(${header.id})")
                .log("Msg: ${body}").enrich("direct:acc", new AggregationStrategyImpl());


        from("direct:acc").setBody().constant(null).to("http://account:8080").unmarshal(format);

    }

    @EventListener(ContextClosedEvent.class)
    public void deregisterFromConsul() {
        try {
            context.createProducerTemplate().sendBody("direct:consul-deregister", null);
        } catch (Exception e) {
            log.warn("Failed to deregister from Consul: {}", e.getMessage());
        }
    }

}
