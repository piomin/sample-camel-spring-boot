package pl.piomin.services.camel.customer.service;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import pl.piomin.services.camel.common.model.Account;
import pl.piomin.services.camel.common.model.Customer;

public class AggregationStrategyImpl implements AggregationStrategy {
 
    @SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange original, Exchange resource) {
        Object originalBody = original.getIn().getBody();
        Object resourceResponse = resource.getIn().getBody();
        Customer customer = (Customer) originalBody;
        customer.setAccounts((List<Account>) resourceResponse);
        return original;
    }

}
