package pl.piomin.services.camel.common.model;

public class Register {

	private String ID;
	private String Name;
	private String Address;
	private int Port;

	public Register() {
	}

	public Register(String ID, String name, String address, int port) {
		this.ID = ID;
		Name = name;
		Address = address;
		Port = port;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
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
