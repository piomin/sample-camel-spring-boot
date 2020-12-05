package pl.piomin.services.camel.customer;

import java.util.EventObject;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.management.event.CamelContextStartedEvent;
import org.apache.camel.management.event.CamelContextStoppingEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pl.piomin.services.camel.common.model.Register;

@Component
public class EventNotifier extends EventNotifierSupport {

	@Value("${port}")
	private int port;
	
	@Override
	public void notify(EventObject event) throws Exception {
		if (event instanceof CamelContextStartedEvent) {
			CamelContext context = ((CamelContextStartedEvent) event).getContext();
			ProducerTemplate t = context.createProducerTemplate();
			t.sendBody("direct:start", new Register("customer" + port, "customer", "127.0.0.1", port));
		}
		if (event instanceof CamelContextStoppingEvent) {
			CamelContext context = ((CamelContextStoppingEvent) event).getContext();
			ProducerTemplate t = context.createProducerTemplate();
			t.sendBodyAndHeader("direct:stop", null, "id", "customer" + port);
		}
	}

	@Override
	public boolean isEnabled(EventObject event) {
		return (event instanceof CamelContextStartedEvent || event instanceof CamelContextStoppingEvent);
	}

}
