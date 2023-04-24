package pl.piomin.services.camel.gateway;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piomin.services.camel.common.model.Account;
import pl.piomin.services.camel.common.model.Customer;

@Component
public class RouteGateway extends RouteBuilder {

    @Autowired
    CamelContext context;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("netty4-http")
                .bindingMode(RestBindingMode.json)
                .port(8000)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Example API").apiProperty("api.version", "1.0")
                .apiProperty("cors", "true");

        JacksonDataFormat formatAcc = new JacksonDataFormat(Account.class);
        JacksonDataFormat formatCus = new JacksonDataFormat(Customer.class);
        JacksonDataFormat formatAccList = new JacksonDataFormat(Account.class);
        formatAccList.useList();
        JacksonDataFormat formatCusList = new JacksonDataFormat(Customer.class);
        formatCusList.useList();

//		rest("/account")
//			.get("/{id}").description("Find account by id").outType(Account.class)
//				.param().name("id").type(RestParamType.path).description("Account identificator").dataType("int").endParam()
//				.route().serviceCall("account").unmarshal(formatAcc)
//				.endRest()
//			.get("/customer/{customerId}").description("Find account by customer id").outType(Account.class)
//				.param().name("customerId").type(RestParamType.path).description("Customer identificator").dataType("int").endParam()
//				.route().serviceCall("account").unmarshal(formatAcc)
//				.endRest()
//			.get("/").description("Find all accounts").outType(List.class)
//				.route().serviceCall("account").unmarshal(formatAccList)
//				.endRest()
//			.post("/").description("Add new account").outType(List.class)
//				.param().name("account").type(RestParamType.body).description("Account JSON object").dataType("Account").endParam()
//				.route().serviceCall("account").unmarshal(formatAcc)
//				.endRest();
//
//		rest("/customer")
//			.get("/{id}").description("Find customer by id").outType(Customer.class)
//				.param().name("id").type(RestParamType.path).description("Customer identificator").dataType("int").endParam()
//				.route().serviceCall("customer").unmarshal(formatCus)
//				.endRest()
//			.get("/").description("Find all customers").outType(List.class)
//				.route().serviceCall("customer").unmarshal(formatCusList)
//				.endRest()
//			.post("/").description("Add new customer").outType(List.class)
//				.param().name("customer").type(RestParamType.body).description("Customer JSON object").dataType("Account").endParam()
//				.route().serviceCall("customer").unmarshal(formatCus)
//				.endRest();

        from("rest:get:account:/{id}").serviceCall("account");
        from("rest:get:account:/customer/{customerId}").serviceCall("account");
        from("rest:get:account:/").serviceCall("account");
        from("rest:post:account:/").serviceCall("account");
        from("rest:get:customer:/{id}").serviceCall("customer");
        from("rest:get:customer:/").serviceCall("customer");
        from("rest:post:customer:/").serviceCall("customer");
    }

}
