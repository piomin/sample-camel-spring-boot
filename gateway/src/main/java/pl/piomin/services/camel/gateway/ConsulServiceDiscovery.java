package pl.piomin.services.camel.gateway;

import org.kiwiproject.consul.Consul;
import org.kiwiproject.consul.model.health.ServiceHealth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsulServiceDiscovery {

    private final Consul consul;

    public ConsulServiceDiscovery(@Value("${camel.component.consul.url:http://localhost:8500}") String consulUrl) {
        this.consul = Consul.builder().withUrl(consulUrl).build();
    }

    public String resolveUrl(String serviceName) {
        List<ServiceHealth> instances = consul.healthClient()
                .getHealthyServiceInstances(serviceName)
                .getResponse();
        ServiceHealth instance = instances.get(0);
        String address = instance.getService().getAddress();
        if (address == null || address.isEmpty()) {
            address = instance.getNode().getAddress();
        }
        return "http://" + address + ":" + instance.getService().getPort();
    }
}