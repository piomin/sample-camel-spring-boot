package pl.piomin.services.camel.customer;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piomin.services.camel.common.model.Account;
import pl.piomin.services.camel.common.model.Customer;
import pl.piomin.services.camel.customer.service.AggregationStrategyImpl;

@Component
public class CustomerRoute extends RouteBuilder {

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
                .port(8080);

//		from("direct:start").routeId("account-consul").marshal().json(JsonLibrary.Jackson)
//			.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
//			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//			.to("http://192.168.99.100:8500/v1/agent/service/register");
//		from("direct:stop").shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
//			.toD("http://192.168.99.100:8500/v1/agent/service/deregister/${header.id}");

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

}
