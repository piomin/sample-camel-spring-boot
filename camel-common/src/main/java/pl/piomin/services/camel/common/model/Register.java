package pl.piomin.services.camel.common.model;

public class Register {

	private String ID;
	private String Name;
	private String Address;
	private int Port;

	public Register() {

	}

	public Register(String iD, String name, String address, int port) {
		ID = iD;
		Name = name;
		Address = address;
		Port = port;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

}
