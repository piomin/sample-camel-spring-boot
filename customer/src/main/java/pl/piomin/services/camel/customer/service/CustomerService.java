package pl.piomin.services.camel.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.piomin.services.camel.customer.model.Customer;

@Service
public class CustomerService {

	private List<Customer> customers = new ArrayList<>();
	
	public Customer findById(Integer id) {
		return new Customer(id, "XXX", "1234567890");
	}
	
	public List<Customer> findAll() {
		List<Customer> l = new ArrayList<>();
		l.add(new Customer(1, "XXX", "1234567890"));
		l.add(new Customer(2, "YYY", "1234567891"));
		return l;
	}
	
	public Customer add(Customer customer) {
		customer.setId(customers.size()+1);
		customers.add(customer);
		return customer;
	}
	
}
