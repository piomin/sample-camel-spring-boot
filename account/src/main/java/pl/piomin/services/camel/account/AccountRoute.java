package pl.piomin.services.camel.account;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.kiwiproject.consul.model.agent.ImmutableRegistration;
import org.kiwiproject.consul.model.agent.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.piomin.services.camel.common.model.Account;

@Component
public class AccountRoute extends RouteBuilder {

    @Value("${camel.netty-http.port:8080}")
    private int port;

    @Autowired
    CamelContext context;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("netty-http")
                .bindingMode(RestBindingMode.json)
                .port(port);

        from("timer:consul-reg?repeatCount=1&delay=2000")
                .routeId("account-consul-register")
                .process(exchange -> {
                    Registration reg = ImmutableRegistration.builder()
                            .id("account-service")
                            .name("account")
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
                .routeId("account-consul-deregister")
                .setBody(constant("account-service"))
                .doTry()
                    .to("consul:agent?action=DEREGISTER")
                .doCatch(Exception.class)
                    .log("Failed to deregister from Consul: ${exception.message}")
                .end();

        rest("/account")
            .get("/{id}")
                .to("bean:accountService?method=findById(${header.id})").produces("application/json")
            .get("/customer/{customerId}")
                .to("bean:accountService?method=findByCustomerId(${header.customerId})")
            .get("/")
                .to("bean:accountService?method=findAll")
            .post("/").consumes("application/json").type(Account.class)
                .to("bean:accountService?method=add(${body})");

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
