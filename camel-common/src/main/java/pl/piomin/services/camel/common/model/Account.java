package pl.piomin.services.camel.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	private Integer id;
	private String number;
	private int amount;
	private Integer customerId;

}
