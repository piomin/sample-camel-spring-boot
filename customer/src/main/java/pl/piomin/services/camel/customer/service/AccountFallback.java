package pl.piomin.services.camel.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.piomin.services.camel.common.model.Account;

@Service
public class AccountFallback {

	public List<Account> getAccounts() {
		return new ArrayList<>();
	}
	
}
