package pl.piomin.services.camel.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Register {

	private String ID;
	private String Name;
	private String Address;
	private int Port;

}
