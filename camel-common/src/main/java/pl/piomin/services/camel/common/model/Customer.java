package pl.piomin.services.camel.common.model;

import java.util.List;

public class Customer {

	private Integer id;
	private String name;
	private String pesel;
	private List<Account> accounts;

	public Customer() {
	}

	public Customer(Integer id, String name, String pesel, List<Account> accounts) {
		this.id = id;
		this.name = name;
		this.pesel = pesel;
		this.accounts = accounts;
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
}
