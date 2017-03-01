package pl.piomin.services.camel.account.service;

import org.springframework.stereotype.Service;

import pl.piomin.services.camel.account.model.Account;

@Service
public class AccountService {

	public Account findById(Integer id) {
		return new Account(id, "1234567890", 4321, 2);
	}
	
}
