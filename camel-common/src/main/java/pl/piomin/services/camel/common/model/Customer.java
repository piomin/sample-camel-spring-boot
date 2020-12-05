package pl.piomin.services.camel.common.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private Integer id;
	private String name;
	private String pesel;
	private List<Account> accounts;
	
}
