package pl.piomin.services.camel.gateway;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RouteGateway extends RouteBuilder {

    final CamelContext context;
    final ConsulServiceDiscovery discovery;

    public RouteGateway(CamelContext context, ConsulServiceDiscovery discovery) {
        this.context = context;
        this.discovery = discovery;
    }

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("netty4-http")
                .bindingMode(RestBindingMode.json)
                .port(8000)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Example API").apiProperty("api.version", "1.0")
                .apiProperty("cors", "true");

        from("rest:get:account:/{id}")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("account")))
                .toD("${header.serviceUrl}/account/${header.id}?bridgeEndpoint=true");

        from("rest:get:account:/customer/{customerId}")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("account")))
                .toD("${header.serviceUrl}/account/customer/${header.customerId}?bridgeEndpoint=true");

        from("rest:get:account:/")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("account")))
                .toD("${header.serviceUrl}/account?bridgeEndpoint=true");

        from("rest:post:account:/")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("account")))
                .toD("${header.serviceUrl}/account?bridgeEndpoint=true");

        from("rest:get:customer:/{id}")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("customer")))
                .toD("${header.serviceUrl}/customer/${header.id}?bridgeEndpoint=true");

        from("rest:get:customer:/")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("customer")))
                .toD("${header.serviceUrl}/customer?bridgeEndpoint=true");

        from("rest:post:customer:/")
                .process(e -> e.getIn().setHeader("serviceUrl", discovery.resolveUrl("customer")))
                .toD("${header.serviceUrl}/customer?bridgeEndpoint=true");
    }

}
