package model;

public class User {
	
	private String name, email, password, type;

	public User(String name, String email, String password, String type) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
	}
	
	public User(String email, String password) {
		// TODO Auto-generated constructor stub
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
