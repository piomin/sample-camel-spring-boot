package pl.piomin.services.camel.customer.model;

import java.util.List;

import pl.piomin.services.camel.common.model.Account;

public class Customer {

	private Integer id;
	private String name;
	private String pesel;
	private List<Account> accounts;

	public Customer() {

	}

	public Customer(Integer id, String name, String pesel) {
		this.id = id;
		this.name = name;
		this.pesel = pesel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
}
