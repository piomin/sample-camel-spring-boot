package pl.piomin.services.camel.account;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.piomin.services.camel.account.model.Register;

@Service
public class Registration {

	@Autowired
	ProducerTemplate template;
	
	public void register() {
//		template.sendBody("direct:start", "{\"ID\": \"acc1\", \"Name\": \"Dupa\", \"Address\": \"127.0.0.1\", \"Port\": 4041}");
		template.sendBody("direct:start", new Register("acc2", "test123", "127.0.0.1", 4040));

	}
	
}
